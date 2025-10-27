package dev.slne.surf.settings.core.impl

import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
data class SettingImpl(
    override val identifier: String,
    override val category: String,
    override val displayName: String,
    override val description: String,
    override val defaultValue: String
) : Setting