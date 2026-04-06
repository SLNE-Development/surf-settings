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

fun lobbySettingsMenu(): AbstractSurfView = surfView("Lobby Einstellungen") {
    val scroll = mutableState(false)

    settings {
        rows(5)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player

        scroll[this] =
            SurfSettingsApi.getPlayerSetting(player.uniqueId, "lobby_scroll_sound")?.getBoolean()
                ?: false

        slot(4, 2) {
            withItem(lobbyScrollItem(scroll[this@onFirstRender]))
            onClick { click ->
                val new = !scroll[this@onFirstRender]
                scroll[this@onFirstRender] = new
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Scroll Sounds nun ")
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
                "lobby_scroll_sound",
                scroll[this@onClose].toString()
            )
        }
    }
}

private fun lobbyScrollItem(state: Boolean) = buildItem(Material.STONE_BUTTON) {
    displayName {
        localColored("Hotbar Scroll Sounds".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Lobby Einstellungen an.") }

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