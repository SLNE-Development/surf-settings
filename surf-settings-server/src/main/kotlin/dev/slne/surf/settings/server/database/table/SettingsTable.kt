package dev.slne.surf.settings.server.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object SettingsTable : LongIdTable("setting_settings") {
    val uid = uuid("uid").uniqueIndex()
    val identifier = varchar("identifier", 255).uniqueIndex()
    val displayName = varchar("display_name", 500)
    val description = text("description")
    val category = varchar("category", 255)
    val defaultValue = varchar("default_value", 256)
}