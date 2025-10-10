package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.server.database.entity.SettingEntity
import dev.slne.surf.settings.server.database.table.SettingsTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository

@Repository
@CoroutineTransactional
class SettingRepository {
    suspend fun query(identifier: String): Setting? =
        SettingEntity.find(SettingsTable.identifier eq identifier).firstOrNull()?.toDto()

    suspend fun queryByCategory(category: String): ObjectSet<Setting> =
        SettingEntity.find(SettingsTable.category eq category).map { it.toDto() }.toObjectSet()

    suspend fun queryInternal(identifier: String) =
        SettingEntity.find(SettingsTable.identifier eq identifier).firstOrNull()

    suspend fun all(): ObjectSet<Setting> = SettingEntity.all().map { it.toDto() }.toObjectSet()
    suspend fun delete(identifier: String) =
        SettingEntity.find(SettingsTable.identifier eq identifier).firstOrNull()?.delete() != null

    suspend fun create(setting: Setting) =
        if (query(setting.identifier) == null) SettingEntity.new {
            this.displayName = setting.displayName
            this.identifier = setting.identifier
            this.category = setting.category
            this.description = setting.description
            this.defaultValue = setting.defaultValue
        }.toDto() else null

    suspend fun createIfNotExists(setting: Setting) =
        query(setting.identifier) ?: create(setting)
}