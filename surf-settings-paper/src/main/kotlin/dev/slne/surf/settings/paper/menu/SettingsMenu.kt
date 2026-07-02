package dev.slne.surf.settings.paper.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.AbstractSurfView
import dev.slne.surf.settings.paper.menu.sub.ChatSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.ClanSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.FriendSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.OtherSettingsMenu
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material


object SettingsMenu : AbstractSurfView("Einstellungen") {
    override fun onViewInit(config: ViewConfigBuilder) {
        config.size(6).cancelInteractions()
    }

    override fun onViewRender(render: RenderContext) {
        render.slot(2, 3) {
            withItem(chatItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(ChatSettingsMenu)
            }
        }

        render.slot(2, 5) {
            withItem(otherItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(OtherSettingsMenu)
            }
        }

        render.slot(2, 7) {
            withItem(friendItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(FriendSettingsMenu)
            }
        }

        render.slot(3, 5) {
            withItem(clanItem())
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(ClanSettingsMenu)
            }
        }

        render.slot(5, 5) {
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

private fun otherItem() = buildItem(Material.GOLD_NUGGET) {
    displayName {
        localColored("Allgemeine Einstellungen".toSmallCaps(), TextDecoration.BOLD)
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