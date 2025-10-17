package dev.slne.surf.settings.api.common

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.serializer.SettingEntrySerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable

@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingEntrySerializer::class)
interface SettingEntry {
    val id: Long
    val player: OfflineCloudPlayer
    val setting: Setting
    var value: String
    val addedAt: Long
    val updatedAt: Long
}