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

fun friendSettingsMenu(): AbstractSurfView = surfView("Freundesystem") {
    val requests = mutableState(false)
    val notify = mutableState(false)
    val sounds = mutableState(false)

    settings {
        rows(5)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player

        requests[this] =
            SurfSettingsApi.getPlayerSetting(
                player.uniqueId,
                "friend-request-notifications-enabled"
            )?.getBoolean()
                ?: true
        notify[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "friend-notifications-enabled")
                ?.getBoolean() ?: true

        sounds[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "friend-sounds-enabled")?.getBoolean()
                ?: true

        slot(3, 3) {
            withItem(friendRequestsItem(requests[this@onFirstRender]))
            onClick { click ->
                val new = !requests[this@onFirstRender]
                requests[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundschaftsanfragen nun ")
                    variableValue(if (new) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(3, 5) {
            withItem(friendNotifyItem(notify[this@onFirstRender]))
            onClick { click ->
                val new = !notify[this@onFirstRender]
                notify[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundesbenachrichtigungen nun ")
                    variableValue(if (new) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
        }

        slot(3, 7) {
            withItem(friendSoundsItem(sounds[this@onFirstRender]))
            onClick { click ->
                val new = !sounds[this@onFirstRender]
                sounds[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundesbenachrichtungstöne nun ")
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
                "friend-request-notifications-enabled",
                requests[this@onClose].toString()
            )
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "friend-notifications-enabled",
                notify[this@onClose].toString()
            )
            SurfSettingsApi.saveSetting(
                player.uniqueId,
                "friend-sounds-enabled",
                sounds[this@onClose].toString()
            )
        }
    }
}

private fun friendRequestsItem(state: Boolean) = buildItem(Material.POPPY) {
    displayName {
        localColored("Freundschaftsanfragen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Freundes Einstellungen an.") }

        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line {
            spacer("-")
            appendSpace()
            localColored(if (state) "Aktiviert" else "Deaktiviert")
        }

        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}

private fun friendSoundsItem(state: Boolean) =
    buildItem(if (state) Material.LIME_CANDLE else Material.RED_CANDLE) {
        displayName {
            localColored("Freundesbenachrichtungstöne".toSmallCaps(), TextDecoration.BOLD)
        }

        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Freundes Einstellungen an.") }

            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line {
                spacer("-")
                appendSpace()
                localColored(if (state) "Aktiviert" else "Deaktiviert")
            }

            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }

private fun friendNotifyItem(state: Boolean) = buildItem(Material.RABBIT_FOOT) {
    displayName {
        localColored("Freundesbenachrichtigungen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Freundes Einstellungen an.") }

        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line {
            spacer("-")
            appendSpace()
            localColored(if (state) "Aktiviert" else "Deaktiviert")
        }

        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}