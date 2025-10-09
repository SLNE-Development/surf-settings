package dev.slne.surf.settings.api.common.setting

import net.kyori.adventure.text.Component

interface ConfiguredSetting {
    val identifier: String
    val displayName: Component
    val description: String
    val value: String
    val updatedAt: Long
}