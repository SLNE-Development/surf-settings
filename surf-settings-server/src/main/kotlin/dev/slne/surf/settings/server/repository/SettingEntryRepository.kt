package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.core.impl.SettingEntryImpl
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.upsert
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@CoroutineTransactional
class SettingEntryRepository {
    suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean = SettingsEntryTable.upsert {
        it[SettingsEntryTable.player] = playerUuid
        it[SettingsEntryTable.setting] = setting.identifier
        it[SettingsEntryTable.value] = value
    }.let {
        true
    }

    suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ): Boolean = SettingsEntryTable.deleteWhere {
        (SettingsEntryTable.player eq playerUUID) and (SettingsEntryTable.setting eq setting.identifier)
    } > 0

    suspend fun getAll(playerUuid: UUID): ObjectSet<SettingEntry> =
        SettingsEntryTable.selectAll().where(SettingsEntryTable.player eq playerUuid).map {
            SettingEntryImpl(
                settingIdentifier = it[SettingsEntryTable.setting],
                value = it[SettingsEntryTable.value]
            )
        }.toObjectSet()

    suspend fun getAll(): ObjectSet<SettingEntry> = SettingsEntryTable.selectAll().map {
        SettingEntryImpl(
            settingIdentifier = it[SettingsEntryTable.setting],
            value = it[SettingsEntryTable.value]
        )
    }.toObjectSet()

    suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting
    ): SettingEntry? = SettingsEntryTable.selectAll()
        .where((SettingsEntryTable.player eq playerUuid) and (SettingsEntryTable.setting eq setting.identifier))
        .firstOrNull()?.let {
            SettingEntryImpl(
                settingIdentifier = it[SettingsEntryTable.setting],
                value = it[SettingsEntryTable.value]
            )
        }
}