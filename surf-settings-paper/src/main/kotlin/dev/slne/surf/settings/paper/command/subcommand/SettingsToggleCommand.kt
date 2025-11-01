package dev.slne.surf.settings.paper.command.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.settings.paper.permission.PermissionRegistry

fun CommandAPICommand.settingsToggleCommand() = subcommand("toggle") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS_TOGGLE)
    playerExecutor { player, args ->

    }
}