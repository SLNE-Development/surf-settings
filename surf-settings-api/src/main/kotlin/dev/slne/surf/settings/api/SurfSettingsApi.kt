package dev.slne.surf.settings.api

import dev.slne.surf.settings.api.player.SettingsPlayer
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

val surfSettingsApi = requiredService<SurfSettingsApi>()

interface SurfSettingsApi {
    fun getPlayer(name: String): SettingsPlayer?
    fun getPlayer(uuid: UUID): SettingsPlayer?

    suspend fun createSetting(name: String, defaultValue: String)
    fun getSetting(name: String): Setting?
    fun getSettings(): ObjectSet<Setting>
}