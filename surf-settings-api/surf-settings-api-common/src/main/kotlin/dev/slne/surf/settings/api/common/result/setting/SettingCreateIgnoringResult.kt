package dev.slne.surf.settings.api.common.result.setting

import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingCreateIgnoringResult {
    data class Success(val setting: Setting) : SettingCreateIgnoringResult()
    data class Failure(val failureReason: SettingCreateIgnoringFailureReason) :
        SettingCreateIgnoringResult()

    enum class SettingCreateIgnoringFailureReason {
        INTERNAL_ERROR,
        CATEGORY_NOT_FOUND
    }

    fun getOrNull() = if (this is Success) this.setting else null
}