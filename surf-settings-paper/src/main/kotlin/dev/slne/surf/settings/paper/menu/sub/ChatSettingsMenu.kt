package dev.slne.surf.settings.paper.menu.sub

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.mutableState
import dev.slne.surf.api.paper.inventory.framework.view.state.set
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.paper.menu.localColored
import dev.slne.surf.settings.paper.menu.playClickSound
import dev.slne.surf.settings.paper.menu.settingsMenu
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.context.SlotClickContext
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Sound

fun chatSettingsMenu(): AbstractSurfView = surfView("Chat") {
    val pingsHolder = mutableState(false)
    val deathMessageHolder = mutableState(false)
    val directMessageHolder = mutableState(false)

    settings {
        rows(5)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player

        pingsHolder[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "chat_pings")?.getBoolean() ?: true
        deathMessageHolder[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "chat_deathmessages")?.getBoolean()
                ?: true
        directMessageHolder[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "direct_messages")?.getBoolean()
                ?: true

        slot(3, 3) {
            withItem(chatPingsItem(pingsHolder[this@onFirstRender]))
            onClick { click ->
                val newValue = !pingsHolder[this@onFirstRender]
                pingsHolder[this@onFirstRender] = newValue

                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Chat Pings nun ")
                    variableValue(if (newValue) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(3, 5) {
            withItem(deathMessagesItem(deathMessageHolder[this@onFirstRender]))
            onClick { click ->
                val newValue = !deathMessageHolder[this@onFirstRender]
                deathMessageHolder[this@onFirstRender] = newValue

                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Todesnachrichten nun ")
                    variableValue(if (newValue) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(3, 7) {
            withItem(directMessagesItem(directMessageHolder[this@onFirstRender]))
            onClick { click ->
                val newValue = !directMessageHolder[this@onFirstRender]
                directMessageHolder[this@onFirstRender] = newValue

                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Direktnachrichten nun ")
                    variableValue(if (newValue) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(5, 5) {
            withItem(buildItem(Material.BARRIER) {
                displayName {
                    localColored("Zurück".toSmallCaps(), TextDecoration.BOLD)
                }
            })
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(settingsMenu())
            }
        }
    }

    onClose {
        val player = this.player

        plugin.launch {
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "chat_pings",
                pingsHolder[this@onClose].toString()
            )
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "chat_deathmessages",
                deathMessageHolder[this@onClose].toString()
            )
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "direct_messages",
                directMessageHolder[this@onClose].toString()
            )
        }
    }
}

private fun SlotClickContext.playClickSound() {
    this.player.playSound(true) {
        type(Sound.UI_BUTTON_CLICK)
    }
}

private fun chatPingsItem(currentState: Boolean) = buildItem(Material.BELL) {
    displayName {
        localColored("Chat Pings".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Chat Einstellungen an.")
        }

        emptyLine()
        line {
            variableValue("Status:".toSmallCaps())
        }

        line {
            spacer("-")
            appendSpace()
            localColored(if (currentState) "Aktiviert" else "Deaktiviert")
        }

        emptyLine()

        line {
            spacer("Klicke, um die Einstellung zu ändern")
        }
    }
}

private fun directMessagesItem(currentState: Boolean) = buildItem(Material.RED_DYE) {
    displayName {
        localColored("Direktnachrichten".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Chat Einstellungen an.")
        }

        emptyLine()
        line {
            variableValue("Status:".toSmallCaps())
        }

        line {
            spacer("-")
            appendSpace()
            localColored(if (currentState) "Aktiviert" else "Deaktiviert")
        }

        emptyLine()

        line {
            spacer("Klicke, um die Einstellung zu ändern")
        }
    }
}

private fun deathMessagesItem(state: Boolean) = buildItem(Material.SKELETON_SKULL) {
    displayName {
        localColored("Todesnachrichten".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Chat Einstellungen an.")
        }

        emptyLine()
        line {
            variableValue("Status:".toSmallCaps())
        }

        line {
            spacer("-")
            appendSpace()
            localColored(if (state) "Aktiviert" else "Deaktiviert")
        }

        emptyLine()

        line {
            spacer("Klicke, um die Einstellung zu ändern")
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)