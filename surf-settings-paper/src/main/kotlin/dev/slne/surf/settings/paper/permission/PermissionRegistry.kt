package dev.slne.surf.settings.paper.permission

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {
    const val BASE = "surf.settings"
    const val BASE_COMMAND = "$BASE.command"

    val COMMAND_SETTINGS = create("$BASE_COMMAND.settings")
    val COMMAND_SURF_SETTINGS = create("$BASE_COMMAND.surfsettings")
    val COMMAND_SURF_SETTINGS_REFRESH = create("$BASE_COMMAND.surfsettings.refresh")
}