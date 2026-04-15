package dev.slne.surf.settings.paper.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.AbstractSurfView
import dev.slne.surf.api.paper.inventory.framework.view.onFirstRender
import dev.slne.surf.api.paper.inventory.framework.view.settings
import dev.slne.surf.api.paper.inventory.framework.view.surfView
import dev.slne.surf.settings.paper.menu.sub.chatSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.clanSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.friendSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.lobbySettingsMenu
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

fun settingsMenu(): AbstractSurfView = surfView("Einstellungen") {
    settings {
        rows(5)
        cancelAllInteractions()
    }

    onFirstRender {
        slot(2, 3) {
            withItem(chatItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(chatSettingsMenu())
            }
        }

        slot(2, 5) {
            withItem(lobbyItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(lobbySettingsMenu())
            }
        }

        slot(2, 7) {
            withItem(friendItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(friendSettingsMenu())
            }
        }

        slot(3, 5) {
            withItem(clanItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(clanSettingsMenu())
            }
        }

        slot(5, 5) {
            withItem(buildItem(Material.BARRIER) {
                displayName {
                    localColored("Schließen".toSmallCaps(), TextDecoration.BOLD)
                }
            })
            onClick { click ->
                click.playClickSound()
                click.closeForPlayer()
            }
        }
    }
}

private fun chatItem() = buildItem(Material.BELL) {
    displayName {
        localColored("Chat Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }
}

private fun lobbyItem() = buildItem(Material.GOLD_NUGGET) {
    displayName {
        localColored("Lobby Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }
}

private fun friendItem() = buildItem(Material.POPPY) {
    displayName {
        localColored("Freundes Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }
}

private fun clanItem() = buildItem(Material.CLOCK) {
    displayName {
        localColored("Clan Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }
}