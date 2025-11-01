package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.serializer.SettingSerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable
import java.util.*

@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingSerializer::class)
interface Setting {
    val uid: UUID
    val identifier: String
    val displayName: String
    val description: String
    val category: String
    val defaultValue: String
}