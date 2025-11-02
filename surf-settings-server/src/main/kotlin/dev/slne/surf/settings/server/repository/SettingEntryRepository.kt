package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.core.impl.SettingEntryImpl
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.coroutines.coroutineScope
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.upsert
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@CoroutineTransactional
class SettingEntryRepository(
    private val settingRepository: SettingRepository
) {
    suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean = SettingsEntryTable.upsert {
        it[SettingsEntryTable.player] = playerUuid
        it[SettingsEntryTable.settingUid] = setting.uid
        it[SettingsEntryTable.value] = value
    }.let {
        true
    }

    suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ): Boolean = SettingsEntryTable.deleteWhere {
        (SettingsEntryTable.player eq playerUUID) and (SettingsEntryTable.settingUid eq setting.uid)
    } > 0

    suspend fun getAll(playerUuid: UUID): ObjectSet<SettingEntry> = coroutineScope {
        val entries = SettingsEntryTable
            .selectAll()
            .where(SettingsEntryTable.player eq playerUuid)
            .toList()


        val settingEntries = entries.mapNotNull { row ->
            val settingUid = row[SettingsEntryTable.settingUid]
            val setting = settingRepository.getSetting(settingUid) ?: return@mapNotNull null

            SettingEntryImpl(
                setting = setting,
                value = row[SettingsEntryTable.value]
            )
        }

        settingEntries.toObjectSet()
    }


    suspend fun getAllByCategoryPlayer(
        playerUuid: UUID,
        category: String
    ): ObjectSet<SettingEntry> = coroutineScope {
        val entries = SettingsEntryTable
            .selectAll()
            .where(SettingsEntryTable.player eq playerUuid)
            .toList()

        entries.mapNotNull { row ->
            val settingUid = row[SettingsEntryTable.settingUid]
            val setting = settingRepository.getSetting(settingUid)
                ?.takeIf { it.category == category }
                ?: return@mapNotNull null

            SettingEntryImpl(
                setting = setting,
                value = row[SettingsEntryTable.value]
            )
        }.toObjectSet()
    }


    suspend fun getAll(): ObjectSet<SettingEntry> = coroutineScope {
        val entries = SettingsEntryTable.selectAll().toList()

        entries.mapNotNull { row ->
            val settingUid = row[SettingsEntryTable.settingUid]
            val setting = settingRepository.getSetting(settingUid) ?: return@mapNotNull null

            SettingEntryImpl(
                setting = setting,
                value = row[SettingsEntryTable.value]
            )
        }.toObjectSet()
    }


    suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting
    ): SettingEntry? = coroutineScope {
        val row = SettingsEntryTable
            .selectAll()
            .where(
                (SettingsEntryTable.player eq playerUuid) and
                        (SettingsEntryTable.settingUid eq setting.uid)
            )
            .firstOrNull() ?: return@coroutineScope null

        val fullSetting = settingRepository.getSetting(row[SettingsEntryTable.settingUid])
            ?: return@coroutineScope null

        SettingEntryImpl(
            setting = fullSetting,
            value = row[SettingsEntryTable.value]
        )
    }

}