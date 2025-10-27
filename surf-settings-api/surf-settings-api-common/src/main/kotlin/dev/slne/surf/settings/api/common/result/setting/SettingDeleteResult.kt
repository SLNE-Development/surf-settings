package dev.slne.surf.settings.api.common.result.setting

import kotlinx.serialization.Serializable

@Serializable
sealed class SettingDeleteResult {
    @Serializable
    class Success : SettingDeleteResult()

    @Serializable
    data class Failure(val failureReason: SettingDeleteFailureReason) : SettingDeleteResult()

    @Serializable
    enum class SettingDeleteFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}