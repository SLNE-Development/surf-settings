package dev.slne.surf.settings.paper.command

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.settings.api.common.surfSettingApi
import dev.slne.surf.settings.paper.command.dialog.settingCategoriesDialog
import dev.slne.surf.settings.paper.command.subcommand.settingsToggleCommand
import dev.slne.surf.settings.paper.permission.PermissionRegistry
import dev.slne.surf.settings.paper.plugin

fun settingsCommand() = commandAPICommand("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)
    settingsToggleCommand()

    playerExecutor { player, _ ->
        plugin.launch {
            player.showDialog(settingCategoriesDialog(surfSettingApi.getCategories()))
        }
    }
}