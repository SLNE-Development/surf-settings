package dev.slne.surf.settings.core.impl

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
data class SettingEntryImpl(
    override val setting: Setting,
    override var value: String
) : SettingEntry