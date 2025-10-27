package dev.slne.surf.settings.api.common.result.entry

import dev.slne.surf.settings.api.common.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingEntryResetResult {
    @Serializable
    data class Success(val setting: SettingEntry) : SettingEntryResetResult()

    @Serializable
    data class Failure(val failureReason: SettingEntryResetFailureReason) :
        SettingEntryResetResult()

    @Serializable
    enum class SettingEntryResetFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): SettingEntry? = if (this is Success) this.setting else null
}