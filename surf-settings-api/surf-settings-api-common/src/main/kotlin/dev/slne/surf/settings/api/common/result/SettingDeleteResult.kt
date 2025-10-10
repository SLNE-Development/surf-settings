package dev.slne.surf.settings.api.common.result

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingDeleteResult {
    class Success : SettingDeleteResult()
    data class Failure(val failureReason: SettingDeleteFailureReason) : SettingDeleteResult()

    enum class SettingDeleteFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}