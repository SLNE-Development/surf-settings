package dev.slne.surf.settings.api.common.result.setting

import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingQueryResult {
    @Serializable
    data class Success(val setting: Setting) : SettingQueryResult()

    @Serializable
    data class Failure(val failureReason: SettingQueryFailureReason) : SettingQueryResult()

    @Serializable
    enum class SettingQueryFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): Setting? = if (this is Success) this.setting else null
}