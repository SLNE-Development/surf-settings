package dev.slne.surf.settings.backend.service

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.backend.repository.playerSettingsRepository
import dev.slne.surf.settings.backend.repository.settingRepository
import dev.slne.surf.settings.core.service.SettingsService
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
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
        getSettingsForPlayer(playerUuid).firstOrNull { it.setting.name == settingName }

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
    ): Setting = settingRepository.createSetting(name, defaultValue)

    override suspend fun deleteSetting(name: String) {
        settingRepository.deleteSetting(name)
    }
}