package dev.slne.surf.settings.api.common.result.category

import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCategoryQueryResult {
    @Serializable
    data class Success(val category: SettingCategory) : SettingCategoryQueryResult()

    @Serializable
    data class Failure(val failureReason: SettingCategoryQueryFailureReason) :
        SettingCategoryQueryResult()

    @Serializable
    enum class SettingCategoryQueryFailureReason {
        CATEGORY_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): SettingCategory? = if (this is Success) this.category else null
}