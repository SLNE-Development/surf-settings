package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.core.impl.SettingImpl
import dev.slne.surf.settings.server.database.table.SettingsTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@CoroutineTransactional
class SettingRepository {
    suspend fun createSetting(
        uid: UUID,
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting? = getSetting(uid) ?: run {
        SettingsTable.insert {
            it[SettingsTable.uid] = uid
            it[SettingsTable.identifier] = identifier
            it[SettingsTable.displayName] = displayName
            it[SettingsTable.description] = description
            it[SettingsTable.category] = category
            it[SettingsTable.defaultValue] = defaultValue
        }
        getSetting(uid)
    }

    suspend fun getCategories() =
        SettingsTable.selectAll().map { it[SettingsTable.category] }.toObjectSet()

    suspend fun delete(uid: UUID): Boolean = SettingsTable.deleteWhere {
        SettingsTable.uid eq uid
    } > 0

    suspend fun getSetting(uid: UUID): Setting? = SettingsTable.selectAll().where(
        SettingsTable.uid eq uid
    ).firstOrNull()?.let {
        SettingImpl(
            uid = it[SettingsTable.uid],
            identifier = it[SettingsTable.identifier],
            category = it[SettingsTable.category],
            displayName = it[SettingsTable.displayName],
            description = it[SettingsTable.description],
            defaultValue = it[SettingsTable.defaultValue]
        )
    }

    suspend fun getSetting(identifier: String): Setting? = SettingsTable.selectAll().where(
        SettingsTable.identifier eq identifier
    ).firstOrNull()?.let {
        SettingImpl(
            uid = it[SettingsTable.uid],
            identifier = it[SettingsTable.identifier],
            category = it[SettingsTable.category],
            displayName = it[SettingsTable.displayName],
            description = it[SettingsTable.description],
            defaultValue = it[SettingsTable.defaultValue]
        )
    }

    suspend fun all(): ObjectSet<Setting> = SettingsTable.selectAll().map {
        SettingImpl(
            uid = it[SettingsTable.uid],
            identifier = it[SettingsTable.identifier],
            category = it[SettingsTable.category],
            displayName = it[SettingsTable.displayName],
            description = it[SettingsTable.description],
            defaultValue = it[SettingsTable.defaultValue]
        )
    }.toObjectSet()
}