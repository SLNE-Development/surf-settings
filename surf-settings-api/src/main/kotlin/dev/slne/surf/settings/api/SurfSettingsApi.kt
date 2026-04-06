package dev.slne.surf.settings.api

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

private val api = requiredService<SurfSettingsApi>()

interface SurfSettingsApi {
    fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting>
    fun getPlayerSetting(playerUuid: UUID, settingName: String): PlayerSetting?

    suspend fun createSetting(name: String, defaultValue: String): Setting
    suspend fun saveSetting(playerUuid: UUID, playerSetting: PlayerSetting)
    suspend fun saveSetting(playerUuid: UUID, settingName: String, settingValue: String)
    fun getSetting(name: String): Setting?
    fun getSettings(): ObjectSet<Setting>

    fun openSettingsGui(playerUuid: UUID)

    companion object : SurfSettingsApi by api
}