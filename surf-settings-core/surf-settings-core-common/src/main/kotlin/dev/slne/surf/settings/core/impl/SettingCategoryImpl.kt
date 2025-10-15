package dev.slne.surf.settings.core.impl

import dev.slne.surf.settings.api.common.SettingCategory
import kotlinx.serialization.Serializable

@Serializable
data class SettingCategoryImpl(
    override val identifier: String,
    override val displayName: String,
    override val description: String
) : SettingCategory