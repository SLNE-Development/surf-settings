package dev.slne.surf.settings.server.repository

import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.core.impl.SettingCategoryImpl
import dev.slne.surf.settings.server.database.table.SettingCategoryTable
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
@CoroutineTransactional
class SettingCategoryRepository {
    suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategory? = getCategory(identifier) ?: run {
        SettingCategoryTable.insert {
            it[SettingCategoryTable.identifier] = identifier
            it[SettingCategoryTable.displayName] = displayName
            it[SettingCategoryTable.description] = description
        }
        getCategory(identifier)
    }

    suspend fun deleteCategory(category: SettingCategory): Boolean =
        SettingCategoryTable.deleteWhere {
            SettingCategoryTable.identifier eq category.identifier
        } > 0

    suspend fun getCategory(identifier: String): SettingCategory? =
        SettingCategoryTable.selectAll().where(SettingCategoryTable.identifier eq identifier)
            .firstOrNull()?.let {
                SettingCategoryImpl(
                    identifier = it[SettingCategoryTable.identifier],
                    displayName = it[SettingCategoryTable.displayName],
                    description = it[SettingCategoryTable.description]
                )
            }

    suspend fun all(): ObjectSet<SettingCategory> = SettingCategoryTable.selectAll().map {
        SettingCategoryImpl(
            identifier = it[SettingCategoryTable.identifier],
            displayName = it[SettingCategoryTable.displayName],
            description = it[SettingCategoryTable.description]
        )
    }.toObjectSet()
}