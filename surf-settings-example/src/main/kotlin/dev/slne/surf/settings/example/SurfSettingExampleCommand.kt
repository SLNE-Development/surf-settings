package dev.slne.surf.settings.example

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.surf.settings.api.common.surfSettingApi
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun toggleExampleBooleanSettingCommand() = commandTree("surfsettingsexample") {
    literalArgument("toggleboolean") {
        playerExecutor { player, _ ->
            plugin.launch {
                val setting =
                    surfSettingApi.querySetting(ExampleMain.ID_BOOLEAN).getOrNull() ?: run {
                        player.sendText {
                            appendPrefix()
                            error("Die Einstellung wurde nicht gefunden!")
                        }
                        return@launch
                    }

                val entry = surfSettingApi.queryEntry(player.uniqueId, setting).getOrNull() ?: run {
                    player.sendText {
                        appendPrefix()
                        error("Die Einstellungseintrag wurde nicht gefunden!")
                    }
                    return@launch
                }

                val currentValue = entry.value
                val newValue = (currentValue.toBoolean().not()).toString()

                surfSettingApi.modifyEntry(player.uniqueId, entry.apply {
                    value = newValue
                })

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
                        surfSettingApi.querySetting(ExampleMain.ID_INT).getOrNull() ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellung wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val entry =
                        surfSettingApi.queryEntry(player.uniqueId, setting).getOrNull() ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellungseintrag wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val currentValue = entry.value
                    val newValue = value.toString()

                    surfSettingApi.modifyEntry(player.uniqueId, entry.apply {
                        this.value = newValue
                    })

                    player.sendText {
                        appendPrefix()
                        success("Die Einstellung wurde von $currentValue auf $newValue geändert.")
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
                        surfSettingApi.querySetting(ExampleMain.ID_TEXT).getOrNull() ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellung wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val entry =
                        surfSettingApi.queryEntry(player.uniqueId, setting).getOrNull() ?: run {
                            player.sendText {
                                appendPrefix()
                                error("Die Einstellungseintrag wurde nicht gefunden!")
                            }
                            return@launch
                        }

                    val currentValue = entry.value
                    val newValue = text

                    surfSettingApi.modifyEntry(player.uniqueId, entry.apply {
                        this.value = newValue
                    })

                    player.sendText {
                        appendPrefix()
                        success("Die Einstellung wurde von $currentValue auf $newValue geändert.")
                    }
                }
            }
        }
    }

    literalArgument("getAllFromMe") {
        playerExecutor { player, _ ->
            plugin.launch {
                val entries = surfSettingApi.allEntries(player.uniqueId)

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
                            info(" - ${entry.setting.displayName} (${entry.setting.identifier}): ${entry.value}")
                        }
                    }
                }
            }
        }
    }
}