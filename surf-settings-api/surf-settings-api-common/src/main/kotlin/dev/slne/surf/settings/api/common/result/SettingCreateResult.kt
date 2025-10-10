package dev.slne.surf.settings.api.common.result

import dev.slne.surf.settings.api.common.setting.Setting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCreateResult {
    data class Success(val setting: Setting) : SettingCreateResult()
    data class Failure(val failureReason: SettingCreateFailureReason) : SettingCreateResult()

    enum class SettingCreateFailureReason {
        SETTING_ALREADY_EXISTS
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}