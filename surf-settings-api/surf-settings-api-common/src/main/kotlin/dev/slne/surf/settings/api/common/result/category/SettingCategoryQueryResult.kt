package dev.slne.surf.settings.api.common.result.category

import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCategoryQueryResult {
    data class Success(val category: SettingCategory) : SettingCategoryQueryResult()
    data class Failure(val failureReason: SettingCategoryQueryFailureReason) :
        SettingCategoryQueryResult()

    enum class SettingCategoryQueryFailureReason {
        CATEGORY_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): SettingCategory? = if (this is Success) this.category else null
}