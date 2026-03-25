package dev.slne.surf.settings.core.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.surfapi.core.api.util.toMutableObjectSet
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {
    private val _playerSettings = mutableObject2ObjectMapOf<UUID, ObjectSet<PlayerSetting>>()

    override val settings = mutableObjectSetOf<Setting>()
    override val playerSettings: ObjectSet<PlayerSetting> =
        _playerSettings.values.flatten().toObjectSet()

    override fun getSettingByName(name: String): Setting? = settings.firstOrNull { it.name == name }
    override fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting> =
        _playerSettings[playerUuid] ?: mutableObjectSetOf()

    override fun getSettingForPlayer(
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

    override fun cachePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        val playerSettings =
            _playerSettings.getOrPut(playerUuid) { mutableObjectSetOf() }.toMutableObjectSet()
        playerSettings.removeIf { it.setting.name == playerSetting.setting.name }
        playerSettings.add(playerSetting)

        _playerSettings[playerUuid] = playerSettings
    }

    override suspend fun savePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        playerSettingsRepository.savePlayerSetting(playerUuid, playerSetting)
    }

    override suspend fun cachePlayerSettings(playerUuid: UUID) {
        _playerSettings[playerUuid] = playerSettingsRepository.loadSettingsByPlayerUuid(playerUuid)
    }

    override fun invalidatePlayerSettingsCache(playerUuid: UUID) {
        _playerSettings.remove(playerUuid)
    }

    override suspend fun refreshSettings() {
        settings.clear()
        settings.addAll(settingRepository.loadSettings())
    }

    override suspend fun createSetting(
        name: String,
        defaultValue: String
    ): Setting {
        val existing = getSettingByName(name)
        if (existing != null) {
            return existing
        }

        return settingRepository.createSetting(name, defaultValue).also {
            settings.add(it)
        }
    }

    override suspend fun deleteSetting(name: String) {
        settingRepository.deleteSetting(name).also {
            settings.removeIf { it.name == name }
        }
    }
}