package dev.slne.surf.settings.api.common.result.category

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCategoryDeleteResult {
    class Success() : SettingCategoryDeleteResult()
    data class Failure(val failureReason: SettingCategoryDeleteFailureReason) :
        SettingCategoryDeleteResult()

    enum class SettingCategoryDeleteFailureReason {
        CATEGORY_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}