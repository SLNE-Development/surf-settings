package dev.slne.surf.settings.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.command.SettingsCommandActions
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages
import dev.slne.surf.settings.paper.command.argument.niceToggleArgument
import dev.slne.surf.settings.paper.command.argument.settingArgument
import dev.slne.surf.settings.paper.menu.settingsView
import dev.slne.surf.settings.paper.permission.PermissionRegistry

fun settingsCommand() = commandTree("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)

    playerExecutor { player, _ ->
        settingsView.open(player)
    }

    settingArgument("setting") {
        playerExecutor { player, args ->
            val setting: Setting by args

            if (!setting.isBoolean()) {
                player.sendMessage(SettingsCommandMessages.booleanOnly)
                return@playerExecutor
            }

            val value = SettingsCommandActions.toggle(player.uniqueId, setting)
            player.sendMessage(SettingsCommandMessages.changed(setting.name, value))
        }


        niceToggleArgument("state") {
            playerExecutor { player, args ->
                val setting: Setting by args
                val state: Boolean by args

                if (!setting.isBoolean()) {
                    player.sendMessage(SettingsCommandMessages.booleanOnly)
                    return@playerExecutor
                }

                val value = SettingsCommandActions.set(player.uniqueId, setting, state)
                player.sendMessage(SettingsCommandMessages.changed(setting.name, value))
            }
        }

        literalArgument("#info") {
            playerExecutor { player, args ->
                val setting: Setting by args
                val value = SettingsCommandActions.get(player.uniqueId, setting)
                player.sendMessage(SettingsCommandMessages.info(setting.name, value))
            }
        }
    }
}
