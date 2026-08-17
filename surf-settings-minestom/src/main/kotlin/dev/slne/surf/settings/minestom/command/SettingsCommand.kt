package dev.slne.surf.settings.minestom.command

import dev.slne.minestom.lobby.api.command.commandapi.dsl.commandTree
import dev.slne.minestom.lobby.api.command.commandapi.dsl.literalArgument
import dev.slne.minestom.lobby.api.command.commandapi.dsl.playerExecutor
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.command.SettingsCommandActions
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages
import dev.slne.surf.settings.core.client.permission.SettingsPermissions
import dev.slne.surf.settings.minestom.command.argument.niceToggleArgument
import dev.slne.surf.settings.minestom.command.argument.settingArgument

fun settingsCommand() = commandTree("settings") {
    withPermission(SettingsPermissions.COMMAND_SETTINGS)

    playerExecutor { player, _ ->
        SurfSettingsApi.openSettingsGui(player.uuid)
    }

    settingArgument("setting") {
        playerExecutor { player, args ->
            val setting: Setting by args

            if (!setting.isBoolean()) {
                player.sendMessage(SettingsCommandMessages.booleanOnly)
                return@playerExecutor
            }

            val value = SettingsCommandActions.toggle(player.uuid, setting)
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

                val value = SettingsCommandActions.set(player.uuid, setting, state)
                player.sendMessage(SettingsCommandMessages.changed(setting.name, value))
            }
        }

        literalArgument("#info") {
            playerExecutor { player, args ->
                val setting: Setting by args
                val value = SettingsCommandActions.get(player.uuid, setting)
                player.sendMessage(SettingsCommandMessages.info(setting.name, value))
            }
        }
    }
}
