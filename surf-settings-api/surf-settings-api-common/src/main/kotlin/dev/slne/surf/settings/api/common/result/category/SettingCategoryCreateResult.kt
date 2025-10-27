package dev.slne.surf.settings.api.common.result.category

import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCategoryCreateResult {
    @Serializable
    data class Success(val category: SettingCategory) : SettingCategoryCreateResult()

    @Serializable
    data class Failure(val failureReason: SettingCategoryCreateFailureReason) :
        SettingCategoryCreateResult()

    @Serializable
    enum class SettingCategoryCreateFailureReason {
        CATEGORY_ALREADY_EXISTS
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull() = if (this is Success) category else null
}