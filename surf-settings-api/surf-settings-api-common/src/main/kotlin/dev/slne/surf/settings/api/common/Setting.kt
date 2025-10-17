package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.serializer.SettingSerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable

@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingSerializer::class)
interface Setting {
    val id: Long
    val identifier: String
    val category: SettingCategory
    val displayName: String
    val description: String
    val defaultValue: String
}