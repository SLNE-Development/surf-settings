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
import dev.slne.surf.surfapi.bukkit.api.inventory.types.SurfChestGui
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

fun openChatSettingsMenu(player: HumanEntity): SurfChestGui =
    menu(buildText { spacer("Chat Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        withBackButton(height)

        var chatPingsEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "chat_pings")?.getBoolean() ?: true
        var directMessagesEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "direct_messages")?.getBoolean()
                ?: true

        val originalChatPingsEnabled = chatPingsEnabled
        val originalDirectMessagesEnabled = directMessagesEnabled

        addPane(
            ToggleButton(
                2, 2, 1, 1, chatPingsEnabled
            ).apply {
                setDisabledItem(GuiItem(chatPingsItem(false)) {
                    chatPingsEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Chat Pings nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(chatPingsItem(true)) {
                    chatPingsEnabled = false
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Chat Pings nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        addPane(
            ToggleButton(
                6, 2, 1, 1, directMessagesEnabled
            ).apply {
                setDisabledItem(GuiItem(directMessagesItem(false)) {
                    directMessagesEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Direktnachrichten nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(directMessagesItem(true)) {
                    directMessagesEnabled = false

                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Direktnachrichten nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        show(player)

        setOnClose {
            plugin.launch {
                if (chatPingsEnabled != originalChatPingsEnabled) {
                    surfSettingsApi.saveSetting(
                        it.player.uniqueId,
                        "chat_pings",
                        chatPingsEnabled.toString()
                    )
                }

                if (directMessagesEnabled != originalDirectMessagesEnabled) {
                    surfSettingsApi.saveSetting(
                        it.player.uniqueId,
                        "direct_messages",
                        directMessagesEnabled.toString()
                    )
                }
            }
        }
    }

private fun HumanEntity.playClickSound() {
    this.playSound(true) {
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