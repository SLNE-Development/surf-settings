package dev.slne.surf.settings.core.impl

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
data class SettingEntryImpl(
    override val id: Long,
    override val player: OfflineCloudPlayer,
    override val setting: Setting,
    override val value: String,
    override val addedAt: Long,
    override val updatedAt: Long
) : SettingEntry