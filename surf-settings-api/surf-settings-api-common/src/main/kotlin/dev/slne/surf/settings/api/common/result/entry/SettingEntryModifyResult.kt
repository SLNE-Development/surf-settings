package dev.slne.surf.settings.api.common.result.entry

import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingEntryModifyResult {
    data class Success(val setting: SettingEntry) : SettingEntryModifyResult()
    data class Failure(val failureReason: SettingModifyResultFailure) : SettingEntryModifyResult()

    enum class SettingModifyResultFailure {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
}