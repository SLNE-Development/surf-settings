package dev.slne.surf.settings.server.database.entity

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntity
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntityClass
import dev.slne.surf.settings.core.setting.entry.SettingEntryImpl
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import org.jetbrains.exposed.dao.id.EntityID

class SettingEntryEntity(id: EntityID<Long>) : AuditableLongEntity(id, SettingsEntryTable) {
    companion object : AuditableLongEntityClass<SettingEntryEntity>(SettingsEntryTable)

    var player by SettingsEntryTable.player
    var value by SettingsEntryTable.value
    var setting by SettingEntity referencedOn SettingsEntryTable.setting

    fun toDto() = SettingEntryImpl(
        id = id.value,
        player = OfflineCloudPlayer[player],
        setting = setting.toDto(),
        value = value,
        addedAt = createdAt.toEpochSecond() * 1_000,
        updatedAt = updatedAt.toEpochSecond() + 1_000,
    )
}