package dev.slne.surf.settings.paper.menu

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.pane.StaticPane
import dev.slne.surf.settings.paper.menu.sub.openChatSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.openClanSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.openFriendSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.openLobbySettingsMenu
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.menu
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestGui
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.HumanEntity

// TODO: Settings Menu
/**
 * - Kategorien:
 * - Chat Settings
 *     - Chat Pings An/Aus
 *     - PMs An/Aus
 * - Lobby Settings
 *      - Hotbar-Scroll-Sounds An/Aus
 * - Friend Settings
 *      - Friend Requests An/Aus
 *      - Friend Nachjumpen An/Aus
 * - Clan Settings
 *      - Clan Einladungen An/Aus
 *      - Clan Chat Nachrichten An/Aus
 */


private const val height = 5
private const val width = 9

fun openSettingsMenu(player: HumanEntity): SurfChestGui =
    menu(buildText { spacer("Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        addPane(
            StaticPane(
                1, 1, width - 2, height - 2
            ).apply {
                addItem(GuiItem(chatSettingsItem) {
                    openChatSettingsMenu(it.whoClicked)
                }, 1, 1)
                addItem(GuiItem(lobbySettingsItem) {
                    openLobbySettingsMenu(it.whoClicked)
                }, 2, 1)
                addItem(GuiItem(friendSettingsItem) {
                    openFriendSettingsMenu(it.whoClicked)
                }, 4, 1)
                addItem(GuiItem(clanSettingsItem) {
                    openClanSettingsMenu(it.whoClicked)
                }, 5, 1)
            }
        )

        show(player)
    }

private val chatSettingsItem = buildItem(Material.RED_CANDLE) {
    displayName {
        localColored("Chat Einstellungen".toSmallCaps(), TextDecoration.BOLD)
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
            spacer("Klicke, um die Chat Einstellungen zu öffnen")
        }
    }
}

private val lobbySettingsItem = buildItem(Material.GOLD_NUGGET) {
    displayName {
        localColored("Lobby Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Lobby Einstellungen an.")
        }

        emptyLine()
        line {
            spacer("Klicke, um die Lobby Einstellungen zu öffnen")
        }
    }
}

private val clanSettingsItem = buildItem(Material.PINK_HARNESS) {
    displayName {
        localColored("Clan Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            error("Achtung: Diese Funktion ist noch in Arbeit!".toSmallCaps(), TextDecoration.BOLD)
        }
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Clan Einstellungen an.")
        }

        emptyLine()
        line {
            spacer("Klicke, um die Clan Einstellungen zu öffnen")
        }
    }
}

private val friendSettingsItem = buildItem(Material.POPPY) {
    displayName {
        localColored("Freundes Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            error("Achtung: Diese Funktion ist noch in Arbeit!".toSmallCaps(), TextDecoration.BOLD)
        }
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Freundes Einstellungen an.")
        }

        emptyLine()
        line {
            spacer("Klicke, um die Freundes Einstellungen zu öffnen")
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)