package dev.slne.surf.settings.api.common.result.entry

import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingEntryResetResult {
    data class Success(val setting: SettingEntry) : SettingEntryResetResult()
    data class Failure(val failureReason: SettingEntryResetFailureReason) :
        SettingEntryResetResult()

    enum class SettingEntryResetFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}