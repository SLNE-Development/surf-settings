package dev.slne.surf.settings.core.client.service

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.emptyObjectSet
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObject2ObjectMapOf
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.common.rabbit.packet.request.*
import dev.slne.surf.settings.core.common.service.SettingsService
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {

    /**
     * Immutable view of the global setting registry.
     */
    private class Registry(val byName: Object2ObjectOpenHashMap<String, Setting>) {
        val all: ObjectSet<Setting> = mutableObjectSetOf<Setting>(byName.size)
            .apply { addAll(byName.values) }
            .freeze()

        companion object {
            val EMPTY = Registry(mutableObject2ObjectMapOf())
        }
    }

    /** Immutable view of one player's cached settings, keyed by [Setting.name]. */
    private class PlayerSettings(val byName: Object2ObjectOpenHashMap<String, PlayerSetting>) {
        val all: ObjectSet<PlayerSetting> = mutableObjectSetOf<PlayerSetting>(byName.size)
            .apply { addAll(byName.values) }
            .freeze()

        /** Returns a new snapshot with [playerSetting] replacing any entry of the same name. */
        fun with(playerSetting: PlayerSetting): PlayerSettings {
            val copy = mutableObject2ObjectMapOf<String, PlayerSetting>(byName.size + 1)
            copy.putAll(byName)
            copy[playerSetting.setting.name] = playerSetting
            return PlayerSettings(copy)
        }
    }

    private val registry = AtomicReference(Registry.EMPTY)
    private val playerCache = ConcurrentHashMap<UUID, PlayerSettings>()

    override val settings: ObjectSet<Setting> get() = registry.get().all

    override val playerSettings: ObjectSet<PlayerSetting>
        get() {
            val result = mutableObjectSetOf<PlayerSetting>()
            for (snapshot in playerCache.values) {
                result.addAll(snapshot.all)
            }
            return result.freeze()
        }

    override fun getSettingByName(name: String): Setting? = registry.get().byName[name]

    override fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting> =
        playerCache[playerUuid]?.all ?: emptyObjectSet()

    override fun getSettingForPlayer(playerUuid: UUID, settingName: String): PlayerSetting? =
        playerCache[playerUuid]?.byName?.get(settingName)

    override fun getSettingForPlayerOrDefault(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? {
        val cached = playerCache[playerUuid]?.byName?.get(settingName)
        if (cached != null) return cached

        val setting = registry.get().byName[settingName] ?: return null
        return PlayerSetting(setting = setting, settingValue = setting.defaultValue)
    }

    override fun getSettingValueOrDefault(playerUuid: UUID, settingName: String): String? {
        val cached = playerCache[playerUuid]?.byName?.get(settingName)
        if (cached != null) return cached.settingValue

        return registry.get().byName[settingName]?.defaultValue
    }

    override fun cachePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        playerCache.computeIfPresent(playerUuid) { _, existing -> existing.with(playerSetting) }
    }

    override suspend fun savePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        ClientSettingsInstance.rabbitApi.sendRequest(
            SavePlayerSettingRequestPacket(
                playerUuid,
                playerSetting
            )
        )
    }

    override suspend fun cachePlayerSettings(playerUuid: UUID) {
        val loaded = ClientSettingsInstance.rabbitApi.sendRequest(
            LoadPlayerSettingsRequestPacket(playerUuid)
        ).playerSettings

        val byName = mutableObject2ObjectMapOf<String, PlayerSetting>(loaded.size)
        val currentSettings = registry.get().byName
        for ((settingName, value) in loaded) {
            val setting = currentSettings[settingName] ?: continue
            byName[settingName] = PlayerSetting(setting = setting, settingValue = value)
        }

        playerCache[playerUuid] = PlayerSettings(byName)
    }

    override fun invalidatePlayerSettingsCache(playerUuid: UUID) {
        playerCache.remove(playerUuid)
    }

    override suspend fun refreshSettings() {
        val loaded = ClientSettingsInstance.rabbitApi.sendRequest(LoadSettingsRequestPacket()).settings

        val byName = mutableObject2ObjectMapOf<String, Setting>(loaded.size)
        for (setting in loaded) {
            byName[setting.name] = setting
        }

        registry.set(Registry(byName))
    }

    override suspend fun createSetting(
        name: String,
        defaultValue: String
    ): Setting {
        registry.get().byName[name]?.let { return it }

        val created = ClientSettingsInstance.rabbitApi.sendRequest(
            CreateSettingRequestPacket(
                name,
                defaultValue
            )
        ).setting

        registry.updateAndGet { current ->
            if (current.byName.containsKey(name)) return@updateAndGet current

            val copy = mutableObject2ObjectMapOf<String, Setting>(current.byName.size + 1)
            copy.putAll(current.byName)
            copy[name] = created
            Registry(copy)
        }

        return created
    }

    override suspend fun deleteSetting(name: String) {
        ClientSettingsInstance.rabbitApi.sendRequest(DeleteSettingRequestPacket(name))

        registry.updateAndGet { current ->
            if (!current.byName.containsKey(name)) return@updateAndGet current

            val copy = mutableObject2ObjectMapOf<String, Setting>(current.byName.size)
            copy.putAll(current.byName)
            copy.remove(name)
            Registry(copy)
        }
    }
}
