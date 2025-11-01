package dev.slne.surf.settings.server.database.table

import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongIdTable

object SettingsEntryTable : AuditableLongIdTable("setting_entries") {
    val player = uuid("player_uuid")
    val settingUid = uuid("setting_uid").references(SettingsTable.uid)
    val value = varchar("value", 256)

    init {
        uniqueIndex("uq_setting_entries_player_setting", player, settingUid)
    }
}