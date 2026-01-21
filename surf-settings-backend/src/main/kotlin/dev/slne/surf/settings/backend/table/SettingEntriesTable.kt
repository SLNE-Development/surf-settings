package dev.slne.surf.settings.backend.table

import dev.slne.surf.database.columns.nativeUuid
import dev.slne.surf.database.table.AuditableLongIdTable

object SettingEntriesTable : AuditableLongIdTable("settings_entries") {
    val settingName = varchar("setting_name", 255).references(SettingsTable.name)
    val playerUuid = nativeUuid("player_uuid")
    val value = text("value")

    init {
        uniqueIndex(settingName, playerUuid)
    }
}