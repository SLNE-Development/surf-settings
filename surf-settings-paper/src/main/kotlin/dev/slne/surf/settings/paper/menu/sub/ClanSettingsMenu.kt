package dev.slne.surf.settings.paper.menu.sub

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
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
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

fun clanSettingsMenu(): AbstractSurfView = surfView("Clansystem") {
    val invites = mutableState(false)
    val chat = mutableState(false)

    settings {
        rows(5)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player

        invites[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "clan_invites")?.getBoolean() ?: true
        chat[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "clan_chat_messages")?.getBoolean()
                ?: true

        slot(3, 3) {
            withItem(clanInvitesItem(invites[this@onFirstRender]))
            onClick { click ->
                val new = !invites[this@onFirstRender]
                invites[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Clan Einladungen nun ")
                    variableValue(if (new) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(3, 7) {
            withItem(clanChatItem(chat[this@onFirstRender]))
            onClick { click ->
                val new = !chat[this@onFirstRender]
                chat[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Clan-Chat nun ")
                    variableValue(if (new) "aktiviert" else "deaktiviert")
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
                "clan_invites",
                invites[this@onClose].toString()
            )
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "clan_chat_messages",
                chat[this@onClose].toString()
            )
        }
    }
}

private fun clanInvitesItem(currentState: Boolean) = buildItem(Material.FIREWORK_ROCKET) {
    displayName {
        localColored("Clan Einladungen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Clan Einstellungen an.")
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

private fun clanChatItem(currentState: Boolean) = buildItem(Material.CLOCK) {
    displayName {
        localColored("Clan Chat".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Clan Einstellungen an.")
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