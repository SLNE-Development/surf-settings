package dev.slne.surf.settings.api.common.result.entry

import dev.slne.surf.settings.api.common.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingEntryQueryResult {
    @Serializable
    data class Success(val setting: SettingEntry) : SettingEntryQueryResult()

    @Serializable
    data class Failure(val failureReason: SettingEntryQueryFailureReason) :
        SettingEntryQueryResult()

    @Serializable
    enum class SettingEntryQueryFailureReason {
        SETTING_NOT_FOUND
    }

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success

    fun getOrNull(): SettingEntry? = if (this is Success) this.setting else null
}