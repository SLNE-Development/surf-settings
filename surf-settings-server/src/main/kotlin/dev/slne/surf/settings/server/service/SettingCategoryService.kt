package dev.slne.surf.settings.server.service

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.result.category.SettingCategoryCreateResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryDeleteResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryQueryResult
import dev.slne.surf.settings.server.repository.SettingCategoryRepository
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Service

@Service
class SettingCategoryService(
    private val settingCategoryRepository: SettingCategoryRepository
) {
    suspend fun query(identifier: String): SettingCategoryQueryResult {
        val category = settingCategoryRepository.query(identifier)
        return if (category != null) SettingCategoryQueryResult.Success(category) else SettingCategoryQueryResult.Failure(
            SettingCategoryQueryResult.SettingCategoryQueryFailureReason.CATEGORY_NOT_FOUND
        )
    }

    suspend fun all(): ObjectSet<SettingCategory> = settingCategoryRepository.all()
    suspend fun create(category: SettingCategory): SettingCategoryCreateResult {
        val created = settingCategoryRepository.create(category)
        return if (created != null) SettingCategoryCreateResult.Success(created) else SettingCategoryCreateResult.Failure(
            SettingCategoryCreateResult.SettingCategoryCreateFailureReason.CATEGORY_ALREADY_EXISTS
        )
    }

    suspend fun delete(identifier: String): SettingCategoryDeleteResult {
        val deleted = settingCategoryRepository.delete(identifier)
        return if (deleted) SettingCategoryDeleteResult.Success() else SettingCategoryDeleteResult.Failure(
            SettingCategoryDeleteResult.SettingCategoryDeleteFailureReason.CATEGORY_NOT_FOUND
        )
    }

    suspend fun delete(category: SettingCategory): SettingCategoryDeleteResult =
        delete(category.identifier)
}