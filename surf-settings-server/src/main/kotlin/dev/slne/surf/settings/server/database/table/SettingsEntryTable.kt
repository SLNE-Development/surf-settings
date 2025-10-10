package dev.slne.surf.settings.server.database.table

import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object SettingsEntryTable : AuditableLongIdTable("setting_entries") {
    val player = uuid("player_uuid")
    val value = varchar("value", 256)
    val setting = reference("setting_id", SettingsTable, onDelete = ReferenceOption.CASCADE)
}