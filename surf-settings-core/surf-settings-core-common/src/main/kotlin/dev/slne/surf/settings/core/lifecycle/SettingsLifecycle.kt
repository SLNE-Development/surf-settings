package dev.slne.surf.settings.core.lifecycle

interface SettingsLifecycle {
    fun onBootstrap() = Unit
    suspend fun onLoad() = Unit
    suspend fun onEnable() = Unit
    suspend fun onDisable() = Unit
}