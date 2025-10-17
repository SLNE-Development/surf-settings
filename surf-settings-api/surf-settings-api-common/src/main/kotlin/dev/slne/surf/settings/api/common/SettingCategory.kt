package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.serializer.SettingCategorySerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable

@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingCategorySerializer::class)
interface SettingCategory {
    val identifier: String
    val displayName: String
    val description: String
}