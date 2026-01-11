package dev.slne.surf.settings.backend.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.backend.table.SettingEntriesTable
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import kotlinx.coroutines.flow.mapNotNull
import org.gradle.internal.impldep.kotlinx.coroutines.flow.toSet

val playerSettingsRepository = PlayerSettingsRepository()

class PlayerSettingsRepository {
    suspend fun loadSettingsByPlayerId(playerId: Long) = suspendTransaction {
        SettingEntriesTable.selectAll().where(SettingEntriesTable.playerId eq playerId).mapNotNull {
            val setting = settingRepository.loadSettingById(it[SettingEntriesTable.settingId])
                ?: return@mapNotNull null
            PlayerSetting(
                setting,
                it[SettingEntriesTable.value]
            )
        }
    }.toSet().toObjectSet()
}