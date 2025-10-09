package dev.slne.surf.settings.api.common.result

import dev.slne.surf.settings.api.common.setting.ConfiguredSetting
import kotlinx.serialization.Serializable

@Serializable
sealed class SettingModifyResult {
    data class Success(val setting: ConfiguredSetting) : SettingModifyResult()
    data class Failure(val failureReason: SettingModifyResultFailure) : SettingModifyResult()

    enum class SettingModifyResultFailure {
        SETTING_NOT_FOUND,
        ALREADY_UP_TO_DATE
    }
}