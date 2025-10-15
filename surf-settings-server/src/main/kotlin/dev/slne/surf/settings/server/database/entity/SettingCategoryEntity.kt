package dev.slne.surf.settings.server.database.entity

import dev.slne.surf.settings.core.impl.SettingCategoryImpl
import dev.slne.surf.settings.server.database.table.SettingCategoryTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SettingCategoryEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SettingCategoryEntity>(SettingCategoryTable)

    var identifier by SettingCategoryTable.identifier
    var displayName by SettingCategoryTable.displayName
    var description by SettingCategoryTable.description

    fun toDto() = SettingCategoryImpl(
        identifier = identifier,
        displayName = displayName,
        description = description
    )
}