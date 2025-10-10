package dev.slne.surf.settings.core.setting.entry

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import kotlinx.serialization.Serializable

@Serializable
class SettingEntryImpl(
    override val id: Long,
    override val player: OfflineCloudPlayer,
    override val setting: Setting,
    override val value: String,
    override val addedAt: Long,
    override val updatedAt: Long
) : SettingEntry