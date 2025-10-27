package dev.slne.surf.settings.server.database.entity

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntity
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntityClass
import dev.slne.surf.settings.core.impl.SettingEntryImpl
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import org.jetbrains.exposed.dao.id.EntityID

class SettingEntryEntity(id: EntityID<Long>) : AuditableLongEntity(id, SettingsEntryTable) {
    companion object : AuditableLongEntityClass<SettingEntryEntity>(SettingsEntryTable)

    var player by SettingsEntryTable.player
    var value by SettingsEntryTable.value
    var setting by SettingEntity referencedOn SettingsEntryTable.setting

    fun toDto(settingIdentifier: String) = SettingEntryImpl(
        player = OfflineCloudPlayer[player],
        settingIdentifier = settingIdentifier,
        value = value
    )
}