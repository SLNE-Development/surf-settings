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

fun openChatSettingsMenu(player: HumanEntity) =
    menu(buildText { spacer("Chat Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        withBackButton(height)

        var chatPingsEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "chat_pings")?.getBoolean() ?: true
        var directMessagesEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "direct_messages")?.getBoolean()
                ?: true

        addPane(
            ToggleButton(
                2, 2, 1, 1, chatPingsEnabled
            ).apply {
                setDisabledItem(GuiItem(chatPingItem(false)) {
                    chatPingsEnabled = true
                })

                setEnabledItem(GuiItem(chatPingItem(true)) {
                    chatPingsEnabled = false
                })
            })

        addPane(
            ToggleButton(
                6, 2, 1, 1, directMessagesEnabled
            ).apply {
                setDisabledItem(GuiItem(directMessagesItem(false)) {
                    directMessagesEnabled = true
                })

                setEnabledItem(GuiItem(directMessagesItem(true)) {
                    directMessagesEnabled = false
                })
            })

        show(player)

        setOnClose {
            plugin.launch {
                surfSettingsApi.saveSetting(
                    it.player.uniqueId,
                    "chat_pings",
                    chatPingsEnabled.toString()
                )
                surfSettingsApi.saveSetting(
                    it.player.uniqueId,
                    "direct_messages",
                    directMessagesEnabled.toString()
                )
            }
        }
    }

private fun chatPingItem(currentState: Boolean) = buildItem(Material.BELL) {
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
            spacer("Klicke, um die Chat Ping Einstellung zu ändern")
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
            spacer("Klicke, um die Direktnachrichten Einstellung zu ändern")
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)