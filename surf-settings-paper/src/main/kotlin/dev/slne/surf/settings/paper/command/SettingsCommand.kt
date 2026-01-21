package dev.slne.surf.settings.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.service.settingsService
import dev.slne.surf.settings.paper.command.argument.niceToggleArgument
import dev.slne.surf.settings.paper.command.argument.settingArgument
import dev.slne.surf.settings.paper.permission.PermissionRegistry
import dev.slne.surf.settings.paper.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun settingsCommand() = commandTree("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)

    playerExecutor { player, _ ->
        if (plugin.isFolia()) {
            player.sendText {
                appendPrefix()
                error("Das Einstellungsmenu ist auf diesem Server nicht verfügbar.")
            }
            return@playerExecutor
        }

        // TODO: Open settings menu GUI
    }

    settingArgument("setting") {
        playerExecutor { player, args ->
            val setting: Setting by args

            if (!setting.isBoolean()) {
                player.sendText {
                    appendPrefix()
                    error("Nur Boolean Einstellungen können über den Befehl geändert werden.")
                }
                return@playerExecutor
            }

            val playerSetting = settingsService.getSettingForPlayer(player.uniqueId, setting.name)
                ?: error("Setting not found")

        }


        niceToggleArgument("state") {
        }
    }
}