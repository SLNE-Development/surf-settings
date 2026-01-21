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

fun openClanSettingsMenu(player: HumanEntity): SurfChestGui =
    menu(buildText { spacer("Clan Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        withBackButton(height)

        var clanInvitesEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "clan_invites")?.getBoolean() ?: true
        var clanChatEnabled =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "clan_chat_messages")?.getBoolean()
                ?: true

        val originalClanInvitesEnabled = clanInvitesEnabled
        val originalClanChatEnabled = clanChatEnabled

        addPane(
            ToggleButton(
                2, 2, 1, 1, clanInvitesEnabled
            ).apply {
                setDisabledItem(GuiItem(clanInvitesItem(false)) {
                    clanInvitesEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Clan Einladungen nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(clanInvitesItem(true)) {
                    clanInvitesEnabled = false
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Clan Einladungen nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        addPane(
            ToggleButton(
                6, 2, 1, 1, clanChatEnabled
            ).apply {
                setDisabledItem(GuiItem(clanChatItem(false)) {
                    clanChatEnabled = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast den Clan-Chat für dich ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(clanChatItem(true)) {
                    clanChatEnabled = false

                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Clan-Chat für dich ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        show(player)

        setOnClose {
            plugin.launch {
                if (clanInvitesEnabled != originalClanInvitesEnabled) {
                    surfSettingsApi.saveSetting(
                        it.player.uniqueId,
                        "clan_invites",
                        clanInvitesEnabled.toString()
                    )
                }

                if (clanChatEnabled != originalClanChatEnabled) {
                    surfSettingsApi.saveSetting(
                        it.player.uniqueId,
                        "clan_chat_messages",
                        clanChatEnabled.toString()
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

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)