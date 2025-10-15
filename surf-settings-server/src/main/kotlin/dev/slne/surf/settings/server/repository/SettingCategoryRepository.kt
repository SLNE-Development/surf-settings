package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.server.database.entity.SettingCategoryEntity
import dev.slne.surf.settings.server.database.table.SettingCategoryTable
import dev.slne.surf.settings.server.database.table.SettingsTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository

@Repository
@CoroutineTransactional
class SettingCategoryRepository {
    suspend fun query(identifier: String): SettingCategory? = SettingCategoryEntity.find(
        SettingCategoryTable.identifier eq identifier
    ).firstOrNull()?.toDto()

    suspend fun queryInternal(identifier: String): SettingCategoryEntity? =
        SettingCategoryEntity.find(
            SettingCategoryTable.identifier eq identifier
        ).firstOrNull()

    suspend fun all(): ObjectSet<SettingCategory> =
        SettingCategoryEntity.all().map { it.toDto() }.toObjectSet()

    suspend fun create(category: SettingCategory) =
        if (query(category.identifier) == null) SettingCategoryEntity.new {
            this.displayName = category.displayName
            this.identifier = category.identifier
            this.description = category.description
        }.toDto() else null

    suspend fun delete(identifier: String) =
        SettingCategoryEntity.find(SettingsTable.identifier eq identifier).firstOrNull()
            ?.delete() != null

    suspend fun delete(category: SettingCategory) =
        SettingCategoryEntity.find(SettingsTable.identifier eq category.identifier).firstOrNull()
            ?.delete() != null
}