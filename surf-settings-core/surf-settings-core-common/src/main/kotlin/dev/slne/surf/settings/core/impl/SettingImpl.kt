package dev.slne.surf.settings.core.impl

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
data class SettingImpl(
    override val id: Long,
    override val identifier: String,
    override val category: SettingCategory,
    override val displayName: String,
    override val description: String,
    override val defaultValue: String
) : Setting