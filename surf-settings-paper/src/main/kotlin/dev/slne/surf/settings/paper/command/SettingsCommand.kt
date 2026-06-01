package dev.slne.surf.settings.paper.command

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.command.argument.niceToggleArgument
import dev.slne.surf.settings.paper.command.argument.settingArgument
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.permission.PermissionRegistry
import dev.slne.surf.settings.paper.plugin

fun settingsCommand() = commandTree("settings") {
    withPermission(PermissionRegistry.COMMAND_SETTINGS)

    playerExecutor { player, _ ->
        SettingsMenu.open(player)
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

            val playerSetting =
                SettingsService.getSettingForPlayerOrDefault(player.uniqueId, setting.name)
                    ?: error("Setting not found")

            playerSetting.toggle()

            SettingsService.cachePlayerSetting(player.uniqueId, playerSetting)
            plugin.launch {
                SettingsService.savePlayerSetting(player.uniqueId, playerSetting)
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
                    SettingsService.getSettingForPlayerOrDefault(player.uniqueId, setting.name)
                        ?: error("Setting not found")

                playerSetting.settingValue = state.toString()

                SettingsService.cachePlayerSetting(player.uniqueId, playerSetting)

                plugin.launch {
                    SettingsService.savePlayerSetting(player.uniqueId, playerSetting)
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

        literalArgument("#info") {
            playerExecutor { player, args ->
                val setting: Setting by args

                val playerSetting =
                    SettingsService.getSettingForPlayerOrDefault(player.uniqueId, setting.name)
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