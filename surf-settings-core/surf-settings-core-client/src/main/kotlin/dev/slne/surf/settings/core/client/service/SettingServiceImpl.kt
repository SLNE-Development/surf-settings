package dev.slne.surf.settings.core.client.service

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.emptyObject2ObjectMap
import dev.slne.surf.api.core.util.emptyObjectSet
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObject2ObjectMapOf
import dev.slne.surf.api.core.util.toObjectSet
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.common.rabbit.packet.request.*
import dev.slne.surf.settings.core.common.service.SettingsService
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {
    private val _playerSettings = ConcurrentHashMap<UUID, ConcurrentHashMap<String, PlayerSetting>>()
    private val settingsLock = Any()

    @Volatile
    private var settingsByName: Object2ObjectMap<String, Setting> = emptyObject2ObjectMap()

    @Volatile
    private var _settings: ObjectSet<Setting> = emptyObjectSet()

    override val settings: ObjectSet<Setting>
        get() = _settings

    override val playerSettings: ObjectSet<PlayerSetting>
        get() = _playerSettings.values.flatMap { it.values }.toObjectSet()

    override fun getSettingByName(name: String): Setting? = settingsByName[name]
    override fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting> =
        _playerSettings[playerUuid]?.values?.toObjectSet() ?: emptyObjectSet()

    override fun getSettingForPlayerOrDefault(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? =
        getSettingForPlayer(playerUuid, settingName) ?: getSettingByName(settingName)?.let { setting ->
            PlayerSetting(
                setting = setting,
                settingValue = setting.defaultValue
            )
        }

    override fun getSettingForPlayer(playerUuid: UUID, settingName: String): PlayerSetting? =
        _playerSettings[playerUuid]?.get(settingName)

    override fun getLoadedSettingsWithDefaults(playerUuid: UUID): ObjectSet<PlayerSetting> {
        val playerSettings = _playerSettings[playerUuid]

        return settings.map { setting ->
            playerSettings?.get(setting.name) ?: PlayerSetting(
                setting = setting,
                settingValue = setting.defaultValue
            )
        }.toObjectSet()
    }

    override fun cachePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        _playerSettings.computeIfPresent(playerUuid) { _, cached ->
            cached.apply { put(playerSetting.setting.name, playerSetting) }
        }
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

    override suspend fun loadPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting> =
        ClientSettingsInstance.rabbitApi.sendRequest(
            LoadPlayerSettingsRequestPacket(playerUuid)
        ).playerSettings.mapNotNull { (name, value) ->
            val setting = getSettingByName(name) ?: return@mapNotNull null
            PlayerSetting(
                setting = setting,
                settingValue = value
            )
        }.toObjectSet()

    override suspend fun cachePlayerSettings(playerUuid: UUID) {
        _playerSettings.putIfAbsent(playerUuid, ConcurrentHashMap())

        val loaded = loadPlayerSettings(playerUuid)

        _playerSettings.computeIfPresent(playerUuid) { _, cached ->
            cached.apply { loaded.forEach { put(it.setting.name, it) } }
        }
    }

    override fun invalidatePlayerSettingsCache(playerUuid: UUID) {
        _playerSettings.remove(playerUuid)
    }

    override suspend fun refreshSettings() {
        val loaded =
            ClientSettingsInstance.rabbitApi.sendRequest(LoadSettingsRequestPacket()).settings

        synchronized(settingsLock) {
            publishSettings(loaded)
        }
    }

    override suspend fun createSetting(
        name: String,
        defaultValue: String
    ): Setting {
        val existing = getSettingByName(name)
        if (existing != null) {
            return existing
        }

        return ClientSettingsInstance.rabbitApi.sendRequest(
            CreateSettingRequestPacket(
                name,
                defaultValue
            )
        ).setting.also { created ->
            synchronized(settingsLock) {
                publishSettings(_settings + created)
            }
        }
    }

    override suspend fun deleteSetting(name: String) {
        ClientSettingsInstance.rabbitApi.sendRequest(
            DeleteSettingRequestPacket(name)
        ).also {
            synchronized(settingsLock) {
                publishSettings(_settings.filterNot { it.name == name })
            }
        }
    }

    private fun publishSettings(values: Collection<Setting>) {
        val byName = mutableObject2ObjectMapOf<String, Setting>(values.size)
        values.forEach { byName[it.name] = it }

        settingsByName = byName.freeze()
        _settings = values.toObjectSet()
    }
}
