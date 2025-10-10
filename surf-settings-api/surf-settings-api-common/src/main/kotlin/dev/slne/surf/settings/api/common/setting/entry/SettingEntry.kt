package dev.slne.surf.settings.api.common.setting.entry

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.setting.Setting

interface SettingEntry {
    val id: Long
    val player: OfflineCloudPlayer
    val setting: Setting
    val value: String
    val addedAt: Long
    val updatedAt: Long
}