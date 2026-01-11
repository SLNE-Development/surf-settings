package dev.slne.surf.settings.backend.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import dev.slne.surf.settings.api.player.SettingsPlayer
import dev.slne.surf.settings.backend.table.SettingPlayerTable
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

val settingPlayerRepository = SettingPlayerRepository()

class SettingPlayerRepository {
    suspend fun loadPlayerByUuid(uuid: UUID): SettingsPlayer? = suspendTransaction {
        val row = SettingPlayerTable.selectAll().where(SettingPlayerTable.playerUuid eq uuid)
            .firstOrNull() ?: return@suspendTransaction null
        val playerId = row[SettingPlayerTable.id].value

        val settings = playerSettingsRepository.loadSettingsByPlayerId(playerId)

        SettingsPlayer(
            uuid = row[SettingPlayerTable.playerUuid],
            name = row[SettingPlayerTable.playerName],
            settings = settings
        )
    }

    suspend fun loadOrCreate(uuid: UUID, name: String): SettingsPlayer =
        loadPlayerByUuid(uuid) ?: savePlayer(
            SettingsPlayer(
                uuid = uuid,
                name = name,
                settings = mutableObjectSetOf()
            )
        )

    suspend fun savePlayer(player: SettingsPlayer): SettingsPlayer = suspendTransaction {
        SettingPlayerTable.upsert {
            it[playerUuid] = player.uuid
            it[playerName] = player.name
        }
        player
    }
}