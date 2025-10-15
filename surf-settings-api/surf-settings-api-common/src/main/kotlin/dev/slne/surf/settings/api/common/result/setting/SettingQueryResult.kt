package dev.slne.surf.settings.api.common.result.setting

import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingQueryResult {
    data class Success(val setting: Setting) : SettingQueryResult()
    data class Failure(val failureReason: SettingQueryFailureReason) : SettingQueryResult()

    enum class SettingQueryFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): Setting? = if (this is Success) this.setting else null
}