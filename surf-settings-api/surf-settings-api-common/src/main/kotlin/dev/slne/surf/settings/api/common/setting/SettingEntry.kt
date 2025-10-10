package dev.slne.surf.settings.api.common.setting

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer

interface SettingEntry {
    val id: Long
    val player: OfflineCloudPlayer
    val setting: Setting
    val value: String
    val addedAt: Long
    val updatedAt: Long
}