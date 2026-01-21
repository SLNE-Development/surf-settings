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

fun openLobbySettingsMenu(player: HumanEntity): SurfChestGui =
    menu(buildText { spacer("Lobby Einstellungen") }, height) {
        withOutline(width, height)
        withOutClicks()
        withBackButton(height)

        var hotbarScrollSounds =
            surfSettingsApi.getPlayerSetting(player.uniqueId, "lobby_scroll_sound")?.getBoolean()
                ?: false
        val originalHotbarScrollSounds = hotbarScrollSounds

        addPane(
            ToggleButton(
                4, 2, 1, 1, hotbarScrollSounds
            ).apply {
                setDisabledItem(GuiItem(lobbyScrollSoundsItem(false)) {
                    hotbarScrollSounds = true
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Scroll Sounds nun ")
                        variableValue("aktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })

                setEnabledItem(GuiItem(lobbyScrollSoundsItem(true)) {
                    hotbarScrollSounds = false
                    it.whoClicked.sendText {
                        appendPrefix()
                        success("Du hast Scroll Sounds nun ")
                        variableValue("deaktiviert")
                        success(".")
                    }
                    it.whoClicked.playClickSound()
                })
            })

        show(player)

        setOnClose {
            plugin.launch {
                if (hotbarScrollSounds != originalHotbarScrollSounds) {
                    surfSettingsApi.saveSetting(
                        it.player.uniqueId,
                        "lobby_scroll_sound",
                        hotbarScrollSounds.toString()
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

private fun lobbyScrollSoundsItem(currentState: Boolean) = buildItem(Material.STONE_BUTTON) {
    displayName {
        localColored("Lobby Hotbar Scroll Sounds".toSmallCaps(), TextDecoration.BOLD)
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