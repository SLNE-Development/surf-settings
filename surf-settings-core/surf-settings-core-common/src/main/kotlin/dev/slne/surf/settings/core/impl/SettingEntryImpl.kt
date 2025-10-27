package dev.slne.surf.settings.core.impl

import dev.slne.surf.settings.api.common.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
data class SettingEntryImpl(
    override val settingIdentifier: String,
    override var value: String
) : SettingEntry