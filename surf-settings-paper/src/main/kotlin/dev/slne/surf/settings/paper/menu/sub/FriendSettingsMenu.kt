package dev.slne.surf.settings.paper.menu.sub

import com.github.shynixn.mccoroutine.folia.launch
import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.pane.component.ToggleButton
import dev.slne.surf.settings.api.surfSettingsApi
import dev.slne.surf.settings.paper.menu.withBackButton
import dev.slne.surf.settings.paper.menu.withOutClicks
import dev.slne.surf.settings.paper.menu.withOutline
import dev.slne.surf.settings.paper.plugin
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.menu
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.playSound
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Sound
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

fun openFriendSettingsMenu(player: HumanEntity) =
    menu(buildText { spacer("Freundes Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        withBackButton(height)

        var friendRequestsEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "friend_requests")?.getBoolean()
                ?: true
        var friendJumpsEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "friend_jumps")?.getBoolean()
                ?: true

        addPane(
            ToggleButton(
                2, 2, 1, 1, friendRequestsEnabled
            ).apply {
                setDisabledItem(GuiItem(friendRequestsItem(false)) {
                    friendRequestsEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Freundschaftsanfragen nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(friendRequestsItem(true)) {
                    friendRequestsEnabled = false
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Freundschaftsanfragen nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        addPane(
            ToggleButton(
                6, 2, 1, 1, friendJumpsEnabled
            ).apply {
                setDisabledItem(GuiItem(friendJumpItem(false)) {
                    friendJumpsEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Nachspringen von Freunden nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(friendJumpItem(true)) {
                    friendJumpsEnabled = false

                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Nachspringen von Freunden nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        show(player)

        setOnClose {
            plugin.launch {
                surfSettingsApi.saveSetting(
                    it.player.uniqueId,
                    "friend_requests",
                    friendRequestsEnabled.toString()
                )
                surfSettingsApi.saveSetting(
                    it.player.uniqueId,
                    "friend_jumps",
                    friendJumpsEnabled.toString()
                )
            }
        }
    }

private fun HumanEntity.playClickSound() {
    this.playSound(true) {
        type(Sound.UI_BUTTON_CLICK)
    }
}

private fun friendRequestsItem(currentState: Boolean) = buildItem(Material.BELL) {
    displayName {
        localColored("Freundschaftsanfragen".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Freundes Einstellungen an.")
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
            spacer("Klicke, um die Freundschaftsanfragen Einstellung zu ändern")
        }
    }
}

private fun friendJumpItem(currentState: Boolean) = buildItem(Material.RED_DYE) {
    displayName {
        localColored("Nachspringen von Freunden".toSmallCaps(), TextDecoration.BOLD)
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            localColored("Passe deine Freundes Einstellungen an.")
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
            spacer("Klicke, um die Nachspringen Einstellung zu ändern")
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)