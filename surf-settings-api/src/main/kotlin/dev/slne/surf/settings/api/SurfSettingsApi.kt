package dev.slne.surf.settings.api

import dev.slne.surf.settings.api.player.SettingsPlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import java.util.*

val surfSettingsApi = requiredService<SurfSettingsApi>()

interface SurfSettingsApi {
    fun getPlayer(name: String): SettingsPlayer?
    fun getPlayer(uuid: UUID): SettingsPlayer?
}