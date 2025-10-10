package dev.slne.surf.settings.server.database.entity

import dev.slne.surf.settings.core.setting.SettingImpl
import dev.slne.surf.settings.server.database.table.SettingsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SettingEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SettingEntity>(SettingsTable)

    var identifier by SettingsTable.identifier
    var displayName by SettingsTable.displayName
    var description by SettingsTable.description
    var defaultValue by SettingsTable.defaultValue

    fun toDto() = SettingImpl(
        id = id.value,
        identifier = identifier,
        displayName = displayName,
        description = description,
        defaultValue = defaultValue,
    )
}