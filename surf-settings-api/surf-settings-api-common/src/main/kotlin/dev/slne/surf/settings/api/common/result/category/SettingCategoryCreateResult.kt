package dev.slne.surf.settings.api.common.result.category

import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCategoryCreateResult {
    data class Success(val category: SettingCategory) : SettingCategoryCreateResult()
    data class Failure(val failureReason: SettingCategoryCreateFailureReason) :
        SettingCategoryCreateResult()

    enum class SettingCategoryCreateFailureReason {
        CATEGORY_ALREADY_EXISTS
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}