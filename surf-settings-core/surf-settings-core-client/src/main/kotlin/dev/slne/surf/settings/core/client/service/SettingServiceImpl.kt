package dev.slne.surf.settings.core.client.service

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.emptyObjectSet
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.api.core.util.toObjectSet
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.common.rabbit.packet.request.*
import dev.slne.surf.settings.core.common.service.SettingsService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {
    private val _playerSettings = ConcurrentHashMap<UUID, ObjectSet<PlayerSetting>>()
    private val settingsLock = Any()

    @Volatile
    private var _settings: ObjectSet<Setting> = emptyObjectSet()

    override val settings: ObjectSet<Setting>
        get() = _settings

    override val playerSettings: ObjectSet<PlayerSetting>
        get() = _playerSettings.values.flatten().toObjectSet()

    override fun getSettingByName(name: String): Setting? = settings.firstOrNull { it.name == name }
    override fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting> =
        _playerSettings[playerUuid] ?: emptyObjectSet()

    override fun getSettingForPlayerOrDefault(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? =
        getSettingsForPlayer(playerUuid).firstOrNull { it.setting.name == settingName } ?: run {
            val setting = getSettingByName(settingName) ?: return null
            PlayerSetting(
                setting = setting,
                settingValue = setting.defaultValue
            )
        }

    override fun getSettingForPlayer(playerUuid: UUID, settingName: String): PlayerSetting? =
        getSettingsForPlayer(playerUuid).firstOrNull { it.setting.name == settingName }

    override fun getLoadedSettingsWithDefaults(playerUuid: UUID): ObjectSet<PlayerSetting> {
        val playerSettings = getSettingsForPlayer(playerUuid).associateBy { it.setting.name }

        return settings.map { setting ->
            playerSettings[setting.name] ?: PlayerSetting(
                setting = setting,
                settingValue = setting.defaultValue
            )
        }.toObjectSet()
    }

    override fun cachePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        _playerSettings.compute(playerUuid) { _, current ->
            val updated = mutableObjectSetOf<PlayerSetting>()
            current?.filterTo(updated) { it.setting.name != playerSetting.setting.name }
            updated.add(playerSetting)
            updated.freeze()
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

    override suspend fun cachePlayerSettings(playerUuid: UUID) {
        _playerSettings[playerUuid] = ClientSettingsInstance.rabbitApi.sendRequest(
            LoadPlayerSettingsRequestPacket(playerUuid)
        ).playerSettings.mapNotNull {
            val setting = getSettingByName(it.first) ?: return@mapNotNull null
            PlayerSetting(
                setting = setting,
                settingValue = it.second
            )
        }.toObjectSet()
    }

    override fun invalidatePlayerSettingsCache(playerUuid: UUID) {
        _playerSettings.remove(playerUuid)
    }

    override suspend fun refreshSettings() {
        val loaded =
            ClientSettingsInstance.rabbitApi.sendRequest(LoadSettingsRequestPacket()).settings

        synchronized(settingsLock) {
            _settings = loaded.toObjectSet()
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
                val updated = mutableObjectSetOf(_settings)
                updated.add(created)
                _settings = updated.freeze()
            }
        }
    }

    override suspend fun deleteSetting(name: String) {
        ClientSettingsInstance.rabbitApi.sendRequest(
            DeleteSettingRequestPacket(name)
        ).also {
            synchronized(settingsLock) {
                val updated = mutableObjectSetOf(_settings)
                updated.removeIf { it.name == name }
                _settings = updated.freeze()
            }
        }
    }
}
