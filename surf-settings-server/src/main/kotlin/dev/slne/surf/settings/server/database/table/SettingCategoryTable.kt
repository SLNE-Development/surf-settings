package dev.slne.surf.settings.server.database.table

import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongIdTable

object SettingCategoryTable : AuditableLongIdTable("setting_categories") {
    val identifier = varchar("identifier", 128).uniqueIndex()
    val displayName = varchar("display_name", 256)
    val description = varchar("description", 512)
}