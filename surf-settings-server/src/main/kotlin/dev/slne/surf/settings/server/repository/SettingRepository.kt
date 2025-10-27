package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.core.impl.SettingImpl
import dev.slne.surf.settings.server.database.table.SettingsTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
@CoroutineTransactional
class SettingRepository {
    suspend fun createSetting(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting? = getSetting(identifier) ?: run {
        SettingsTable.insert {
            it[SettingsTable.identifier] = identifier
            it[SettingsTable.category] = category.identifier
            it[SettingsTable.displayName] = displayName
            it[SettingsTable.description] = description
            it[SettingsTable.defaultValue] = defaultValue
        }
        getSetting(identifier)
    }

    suspend fun delete(identifier: String): Boolean = SettingsTable.deleteWhere {
        SettingsTable.identifier eq identifier
    } > 0

    suspend fun getSetting(identifier: String): Setting? = SettingsTable.selectAll().where(
        SettingsTable.identifier eq identifier
    ).firstOrNull()?.let {
        SettingImpl(
            identifier = it[SettingsTable.identifier],
            category = it[SettingsTable.category],
            displayName = it[SettingsTable.displayName],
            description = it[SettingsTable.description],
            defaultValue = it[SettingsTable.defaultValue]
        )
    }

    suspend fun all(): ObjectSet<Setting> = SettingsTable.selectAll().map {
        SettingImpl(
            identifier = it[SettingsTable.identifier],
            category = it[SettingsTable.category],
            displayName = it[SettingsTable.displayName],
            description = it[SettingsTable.description],
            defaultValue = it[SettingsTable.defaultValue]
        )
    }.toObjectSet()
}