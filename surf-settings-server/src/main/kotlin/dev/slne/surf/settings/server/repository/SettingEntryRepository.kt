package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.SettingEntry
import dev.slne.surf.settings.server.database.entity.SettingEntryEntity
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@CoroutineTransactional
class SettingEntryRepository(
    private val settingRepository: SettingRepository
) {
    suspend fun all(playerUuid: UUID) =
        SettingEntryEntity.find(SettingsEntryTable.player eq playerUuid).map { it.toDto() }
            .toObjectSet()

    suspend fun query(playerUuid: UUID, setting: Setting) = SettingEntryEntity
        .find((SettingsEntryTable.player eq playerUuid) and (SettingsEntryTable.setting eq setting.id))
        .firstOrNull()

    suspend fun modify(playerUUID: UUID, settingEntry: SettingEntry): SettingEntry? {
        val setting =
            settingRepository.queryInternal(settingEntry.setting.identifier) ?: return null

        val updated = query(playerUUID, settingEntry.setting)?.apply {
            value = settingEntry.value
        } ?: SettingEntryEntity.new {
            player = playerUUID
            value = settingEntry.value
            this.setting = setting
        }

        return updated.toDto()
    }

    suspend fun all() = SettingEntryEntity.all().map { it.toDto() }.toObjectSet()
    suspend fun reset(playerUuid: UUID, setting: Setting) = query(playerUuid, setting)?.apply {
        value = setting.defaultValue
    }
}