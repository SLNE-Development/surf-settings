package dev.slne.surf.settings.paper.command

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.service.settingsService
import dev.slne.surf.settings.paper.command.argument.niceToggleArgument
import dev.slne.surf.settings.paper.command.argument.settingArgument
import dev.slne.surf.settings.paper.menu.openSettingsMenu
import dev.slne.surf.settings.paper.permission.PermissionRegistry
import dev.slne.surf.settings.paper.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun settingsCommand() = commandTree("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)

    playerExecutor { player, _ ->
        if (plugin.isFolia()) {
            player.sendText {
                appendErrorPrefix()
                error("Das Einstellungsmenu ist auf diesem Server nicht verfügbar.")
            }
            return@playerExecutor
        }

        openSettingsMenu(player)
    }

    settingArgument("setting") {
        playerExecutor { player, args ->
            val setting: Setting by args

            if (!setting.isBoolean()) {
                player.sendText {
                    appendErrorPrefix()
                    error("Nur Boolean Einstellungen können über den Befehl geändert werden.")
                }
                return@playerExecutor
            }

            val playerSetting = settingsService.getSettingForPlayer(player.uniqueId, setting.name)
                ?: error("Setting not found")

            playerSetting.toggle()

            settingsService.cachePlayerSetting(player.uniqueId, playerSetting)
            plugin.launch {
                settingsService.savePlayerSetting(player.uniqueId, playerSetting)
            }

            player.sendText {
                appendSuccessPrefix()
                success("Die Einstellung ")
                variableValue(setting.name)
                success(" wurde auf ")
                variableValue(playerSetting.getString())
                success(" gesetzt.")
            }
        }


        niceToggleArgument("state") {
            playerExecutor { player, args ->
                val setting: Setting by args
                val state: Boolean by args

                if (!setting.isBoolean()) {
                    player.sendText {
                        appendErrorPrefix()
                        error("Nur Boolean Einstellungen können über den Befehl geändert werden.")
                    }
                    return@playerExecutor
                }

                val playerSetting =
                    settingsService.getSettingForPlayer(player.uniqueId, setting.name)
                        ?: error("Setting not found")

                playerSetting.settingValue = state.toString()

                settingsService.cachePlayerSetting(player.uniqueId, playerSetting)

                plugin.launch {
                    settingsService.savePlayerSetting(player.uniqueId, playerSetting)
                }

                player.sendText {
                    appendSuccessPrefix()
                    success("Die Einstellung ")
                    variableValue(setting.name)
                    success(" wurde auf ")
                    variableValue(playerSetting.getString())
                    success(" gesetzt.")
                }
            }
        }

        literalArgument("info") {
            playerExecutor { player, args ->
                val setting: Setting by args

                val playerSetting =
                    settingsService.getSettingForPlayer(player.uniqueId, setting.name)
                        ?: error("Setting not found")

                player.sendText {
                    appendInfoPrefix()
                    info("Die Einstellung ")
                    variableValue(playerSetting.setting.name)
                    info(" hat den Wert ")
                    variableValue(playerSetting.getString())
                    info(".")
                }
            }
        }
    }
}