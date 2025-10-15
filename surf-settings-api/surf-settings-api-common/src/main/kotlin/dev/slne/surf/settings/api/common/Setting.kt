package dev.slne.surf.settings.api.common

interface Setting {
    val id: Long
    val identifier: String
    val category: SettingCategory
    val displayName: String
    val description: String
    val defaultValue: String
}