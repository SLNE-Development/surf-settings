package dev.slne.surf.settings.minestom.menu

import dev.slne.surf.api.minestom.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.minestom.inventory.framework.dsl.slot
import dev.slne.surf.api.minestom.inventory.framework.view.AbstractSurfView
import dev.slne.surf.api.minestom.inventory.framework.view.settings.SimpleViewSettings
import dev.slne.surf.api.minestom.inventory.framework.view.settings.ViewRows
import dev.slne.surf.settings.minestom.menu.sub.ChatSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.ClanSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.FriendSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.OtherSettingsMenu
import me.devnatan.inventoryframework.context.RenderContext
import net.minestom.server.item.Material

object SettingsMenu : AbstractSurfView("Einstellungen") {
    override val settings = SimpleViewSettings(
        rows = ViewRows.SIX,
        navigateBackOnOutsideClick = false
    )

    override fun onViewRender(render: RenderContext) {
        render.slot(2, 3) {
            withItem(menuItem(Material.BELL, "Chat Einstellungen"))
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(ChatSettingsMenu)
            }
        }

        render.slot(2, 5) {
            withItem(menuItem(Material.GOLD_NUGGET, "Allgemeine Einstellungen"))
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(OtherSettingsMenu)
            }
        }

        render.slot(2, 7) {
            withItem(menuItem(Material.POPPY, "Freundes Einstellungen"))
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(FriendSettingsMenu)
            }
        }

        render.slot(3, 5) {
            withItem(menuItem(Material.CLOCK, "Clan Einstellungen"))
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(ClanSettingsMenu)
            }
        }

        render.slot(5, 5) {
            withItem(menuItem(Material.BARRIER, "Schließen"))
            onClick { click ->
                click.playClickSound()
                click.closeForPlayer()
            }
        }
    }
}
