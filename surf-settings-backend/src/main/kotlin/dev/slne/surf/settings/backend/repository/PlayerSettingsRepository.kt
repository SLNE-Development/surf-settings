package dev.slne.surf.settings.backend.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.backend.table.SettingEntriesTable
import dev.slne.surf.settings.core.service.settingsService
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toSet
import java.util.*

val playerSettingsRepository = PlayerSettingsRepository()

class PlayerSettingsRepository {
    suspend fun loadSettingsByPlayerUuid(playerUuid: UUID) = suspendTransaction {
        SettingEntriesTable.selectAll().where(SettingEntriesTable.playerUuid eq playerUuid)
            .mapNotNull {
                val setting = settingsService.getSettingByName(it[SettingEntriesTable.settingName])
                    ?: return@mapNotNull null
                PlayerSetting(
                    setting,
                    it[SettingEntriesTable.value]
                )
            }
    }.toSet().toObjectSet()

    suspend fun savePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) = suspendTransaction {
        SettingEntriesTable.upsert {
            it[SettingEntriesTable.playerUuid] = playerUuid
            it[SettingEntriesTable.settingName] = playerSetting.setting.name
            it[SettingEntriesTable.value] = playerSetting.settingValue
        }
    }
}