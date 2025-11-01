package dev.slne.surf.settings.paper.permission

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {
    const val BASE = "surf.settings"

    val COMMAND_SETTINGS = create("$BASE.command.settings")
    val COMMAND_SETTINGS_TOGGLE = create("$BASE.command.settings.toggle")
}