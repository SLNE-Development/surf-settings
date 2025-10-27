package dev.slne.surf.settings.api.common.result.setting

import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCreateResult {
    @Serializable
    data class Success(val setting: Setting) : SettingCreateResult()

    @Serializable
    data class Failure(val failureReason: SettingCreateFailureReason) : SettingCreateResult()

    @Serializable
    enum class SettingCreateFailureReason {
        SETTING_ALREADY_EXISTS,
        CATEGORY_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull() = if (this is Success) this.setting else null
}