package dev.slne.surf.settings.api.common.setting

interface Setting {
    val id: Long
    val identifier: String
    val displayName: String
    val description: String
    val defaultValue: String
}