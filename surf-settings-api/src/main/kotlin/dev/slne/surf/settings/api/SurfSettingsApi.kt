package dev.slne.surf.settings.api

import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

val surfSettingsApi = requiredService<SurfSettingsApi>()

interface SurfSettingsApi {
    fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting>

    suspend fun createSetting(name: String, defaultValue: String): Setting
    fun getSetting(name: String): Setting?
    fun getSettings(): ObjectSet<Setting>
}