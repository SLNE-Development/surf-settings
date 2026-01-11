package dev.slne.surf.settings.backend.service

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.player.SettingsPlayer
import dev.slne.surf.settings.backend.repository.settingPlayerRepository
import dev.slne.surf.settings.core.SettingPlayerService
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SettingPlayerService::class)
class SettingPlayerServiceImpl : SettingPlayerService, Services.Fallback {
    private val players = mutableObject2ObjectMapOf<UUID, SettingsPlayer>()
    override fun cachePlayer(player: SettingsPlayer) {
        players[player.uuid] = player
    }

    override fun invalidatePlayer(uuid: UUID) {
        players.remove(uuid)
    }

    override fun getPlayers(): ObjectSet<SettingsPlayer> = players.values.toObjectSet()

    override fun getPlayer(name: String): SettingsPlayer? =
        players.values.find { it.name.equals(name, ignoreCase = true) }

    override fun getPlayer(uuid: UUID): SettingsPlayer? = players[uuid]

    override suspend fun loadPlayerByUuid(uuid: UUID): SettingsPlayer? =
        settingPlayerRepository.loadPlayerByUuid(uuid)

    override suspend fun getOrLoadPlayerByUuid(uuid: UUID): SettingsPlayer? =
        getPlayer(uuid) ?: settingPlayerRepository.loadPlayerByUuid(uuid)

    override suspend fun getOrLoadOrCreatePlayerByUuid(
        uuid: UUID,
        name: String
    ): SettingsPlayer =
        getPlayer(uuid) ?: settingPlayerRepository.loadOrCreate(uuid, name)

    override suspend fun savePlayer(player: SettingsPlayer) {
        settingPlayerRepository.savePlayer(player)
    }
}