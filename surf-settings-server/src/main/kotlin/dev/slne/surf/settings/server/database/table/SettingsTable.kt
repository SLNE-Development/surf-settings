package dev.slne.surf.settings.server.database.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object SettingsTable : LongIdTable("setting_settings") {
    val identifier = varchar("identifier", 255).uniqueIndex()
    val category =
        reference("category_id", SettingCategoryTable, onDelete = ReferenceOption.CASCADE)
    val displayName = varchar("display_name", 128)
    val description = text("description")
    val defaultValue = varchar("default_value", 256)
}