package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.player.SettingsPlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

val settingPlayerService = requiredService<SettingPlayerService>()

interface SettingPlayerService {
    val onlinePlayers: ObjectSet<SettingsPlayer>
    fun getPlayer(name: String): SettingsPlayer?
    fun getPlayer(uuid: UUID): SettingsPlayer?

    suspend fun loadPlayerByUuid(uuid: UUID): SettingsPlayer?
    suspend fun getOrLoadPlayerByUuid(uuid: UUID): SettingsPlayer
    suspend fun getOrLoadOrCreatePlayerByUuid(uuid: UUID, name: String): SettingsPlayer
    suspend fun savePlayer(player: SettingsPlayer)
}