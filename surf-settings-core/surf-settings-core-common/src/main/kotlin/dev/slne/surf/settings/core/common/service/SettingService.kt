package dev.slne.surf.settings.core.common.service

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

private val service = requiredService<SettingsService>()

interface SettingsService {
    val settings: ObjectSet<Setting>
    val playerSettings: ObjectSet<PlayerSetting>

    fun getSettingByName(name: String): Setting?
    fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting>
    fun getSettingForPlayerOrDefault(playerUuid: UUID, settingName: String): PlayerSetting?
    fun getSettingForPlayer(playerUuid: UUID, settingName: String): PlayerSetting?

    fun cachePlayerSetting(playerUuid: UUID, playerSetting: PlayerSetting)
    suspend fun savePlayerSetting(playerUuid: UUID, playerSetting: PlayerSetting)

    suspend fun cachePlayerSettings(playerUuid: UUID)
    fun invalidatePlayerSettingsCache(playerUuid: UUID)

    suspend fun refreshSettings()
    suspend fun createSetting(name: String, defaultValue: String): Setting
    suspend fun deleteSetting(name: String)

    companion object : SettingsService by service
}