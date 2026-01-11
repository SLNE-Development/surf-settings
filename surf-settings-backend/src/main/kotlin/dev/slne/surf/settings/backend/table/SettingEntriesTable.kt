package dev.slne.surf.settings.backend.table

import dev.slne.surf.database.table.AuditableLongIdTable

object SettingEntriesTable : AuditableLongIdTable("settings_entries") {
    val settingId = ulong("setting_id").references(SettingsTable.id)
    val playerId = long("player_id").references(SettingPlayerTable.id)
    val value = text("value")

    init {
        uniqueIndex(settingId, playerUuid)
    }
}