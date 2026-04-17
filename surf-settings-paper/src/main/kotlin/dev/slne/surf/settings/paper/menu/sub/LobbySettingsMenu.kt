package dev.slne.surf.settings.paper.menu.sub

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.AbstractSurfView
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.paper.SettingKeys
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.menu.localColored
import dev.slne.surf.settings.paper.menu.playClickSound
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.CloseContext
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

object LobbySettingsMenu : AbstractSurfView("Lobby") {
    val scroll = mutableState(false)
    val joinScroll = mutableState(false)

    override fun onViewInit(config: ViewConfigBuilder) {
        config.size(5).cancelInteractions()
    }

    override fun onViewRender(render: RenderContext) {
        val player = render.player

        scroll.set(
            SurfSettingsApi.getSettingValue(player.uniqueId, SettingKeys.LOBBY_SCROLL_SOUND), render
        )

        joinScroll.set(
            SurfSettingsApi.getSettingValue(player.uniqueId, SettingKeys.LOBBY_SCROLL_SOUND), render
        )

        render.slot(3, 5) {
            renderWith {
                lobbyScrollItem(scroll[render])
            }
            onClick { click ->
                val new = !scroll[render]
                scroll.set(new, render)

                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Scroll Sounds nun ")
                    variableValue(if (new) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(scroll)
        }

        render.slot(5, 5) {
            withItem(buildItem(Material.BARRIER) {
                displayName {
                    localColored("Zurück".toSmallCaps(), TextDecoration.BOLD)
                }
            })
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(SettingsMenu)
            }
        }
    }

    override fun onViewClose(close: CloseContext) {
        val player = close.player

        plugin.launch {
            if (joinScroll[close] != scroll[close]) {
                SurfSettingsApi.saveSetting(
                    player.uniqueId,
                    SettingKeys.LOBBY_SCROLL_SOUND,
                    scroll.get(close)
                )
            }
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