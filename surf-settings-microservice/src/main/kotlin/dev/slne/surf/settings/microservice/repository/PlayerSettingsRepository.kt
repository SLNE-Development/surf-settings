package dev.slne.surf.settings.microservice.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.select
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.microservice.table.SettingEntriesTable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.util.*

val playerSettingsRepository = PlayerSettingsRepository()

class PlayerSettingsRepository {
    suspend fun loadSettingsByPlayerUuid(playerUuid: UUID) = suspendTransaction {
        SettingEntriesTable
            .select(SettingEntriesTable.settingName, SettingEntriesTable.value)
            .where(SettingEntriesTable.playerUuid eq playerUuid)
            .map {
                it[SettingEntriesTable.settingName] to it[SettingEntriesTable.value]
            }.toList()
    }

    suspend fun savePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) = suspendTransaction {
        SettingEntriesTable.upsert(SettingEntriesTable.playerUuid, SettingEntriesTable.settingName) {
            it[SettingEntriesTable.playerUuid] = playerUuid
            it[SettingEntriesTable.settingName] = playerSetting.setting.name
            it[SettingEntriesTable.value] = playerSetting.settingValue
        }
    }
}
