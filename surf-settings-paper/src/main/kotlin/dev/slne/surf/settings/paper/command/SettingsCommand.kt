package dev.slne.surf.settings.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.slne.surf.settings.paper.permission.PermissionRegistry

fun settingsCommand() = commandTree("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)
}