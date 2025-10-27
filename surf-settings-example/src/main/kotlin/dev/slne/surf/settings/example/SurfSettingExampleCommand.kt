package dev.slne.surf.settings.example

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.surf.settings.api.common.surfSettingApi
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun surfSettingExampleCommand() = commandTree("surfsettingsexample") {
    literalArgument("toggleboolean") {
        playerExecutor { player, _ ->
            plugin.launch {
                val setting =
                    surfSettingApi.getSetting(ExampleMain.ID_BOOLEAN) ?: run {
                        player.sendText {
                            appendPrefix()
                            error("Die Einstellung wurde nicht gefunden!")
                        }
                        return@launch
                    }

                val entry = surfSettingApi.getEntry(player.uniqueId, setting)

                val currentValue = entry?.value ?: setting.defaultValue
                val newValue = (currentValue.toBoolean().not()).toString()

                surfSettingApi.modifyEntry(player.uniqueId, setting, newValue)

                player.sendText {
                    appendPrefix()
                    success("Die Einstellung wurde von $currentValue auf $newValue geändert.")
                }
            }
        }
    }

    literalArgument("setInt") {
        integerArgument("value") {
            playerExecutor { player, args ->
                val value: Int by args

                plugin.launch {
                    val setting =
                        surfSettingApi.getSetting(ExampleMain.ID_INT) ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellung wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val newValue = value.toString()

                    surfSettingApi.modifyEntry(player.uniqueId, setting, newValue)

                    player.sendText {
                        appendPrefix()
                        success("Die Einstellung wurde auf $newValue geändert.")
                    }
                }
            }
        }
    }

    literalArgument("setText") {
        greedyStringArgument("text") {
            playerExecutor { player, args ->
                val text: String by args

                plugin.launch {
                    val setting =
                        surfSettingApi.getSetting(ExampleMain.ID_TEXT) ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellung wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val newValue = text

                    surfSettingApi.modifyEntry(player.uniqueId, setting, newValue)

                    player.sendText {
                        appendPrefix()
                        success("Die Einstellung wurde auf $newValue geändert.")
                    }
                }
            }
        }
    }

    literalArgument("getAllFromMe") {
        playerExecutor { player, _ ->
            plugin.launch {
                val entries = surfSettingApi.getEntries(player.uniqueId)

                if (entries.isEmpty()) {
                    player.sendText {
                        appendPrefix()
                        info("Du hast keine Einstellungen gesetzt.")
                    }
                    return@launch
                }

                player.sendText {
                    appendPrefix()
                    info("Deine Einstellungen:")
                    entries.forEach { entry ->
                        appendNewline {
                            info(" - ${entry.settingIdentifier}: ${entry.value}")
                        }
                    }
                }
            }
        }
    }
}