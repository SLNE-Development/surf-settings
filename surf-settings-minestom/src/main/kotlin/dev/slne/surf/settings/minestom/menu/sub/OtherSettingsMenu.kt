package dev.slne.surf.settings.minestom.menu.sub

import dev.slne.surf.api.minestom.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.minestom.inventory.framework.dsl.slot
import dev.slne.surf.api.minestom.inventory.framework.view.AbstractSurfView
import dev.slne.surf.api.minestom.inventory.framework.view.settings.SimpleViewSettings
import dev.slne.surf.api.minestom.inventory.framework.view.settings.ViewRows
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.SettingKeys
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.minestom.menu.SettingsMenu
import dev.slne.surf.settings.minestom.menu.menuItem
import dev.slne.surf.settings.minestom.menu.playClickSound
import dev.slne.surf.settings.minestom.menu.sendSettingChanged
import dev.slne.surf.settings.minestom.menu.settingItem
import me.devnatan.inventoryframework.context.CloseContext
import me.devnatan.inventoryframework.context.RenderContext
import net.minestom.server.item.Material

object OtherSettingsMenu : AbstractSurfView("Allgemein") {
    override val settings = SimpleViewSettings(
        rows = ViewRows.FIVE,
        navigateBackOnOutsideClick = false
    )

    private val scroll = mutableState(false)
    private val initialScroll = mutableState(false)
    private val nametag = mutableState(false)
    private val initialNametag = mutableState(false)
    private val scoreboard = mutableState(false)
    private val initialScoreboard = mutableState(false)

    override fun onViewRender(render: RenderContext) {
        val player = render.player
        val scrollValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.LOBBY_SCROLL_SOUND)
        val nametagValue = SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.SHOW_NAMETAGS)
        val scoreboardValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.SHOW_SCOREBOARD)

        scroll.set(scrollValue, render)
        initialScroll.set(scrollValue, render)
        nametag.set(nametagValue, render)
        initialNametag.set(nametagValue, render)
        scoreboard.set(scoreboardValue, render)
        initialScoreboard.set(scoreboardValue, render)

        render.slot(3, 3) {
            renderWith { nameTagItem(nametag[render]) }
            onClick { click ->
                val newValue = !nametag[render]
                nametag.set(newValue, render)
                click.player.sendSettingChanged("Du hast Nametags nun ", newValue)
                click.playClickSound()
            }
            watch(nametag)
        }

        render.slot(3, 5) {
            renderWith { lobbyScrollItem(scroll[render]) }
            onClick { click ->
                val newValue = !scroll[render]
                scroll.set(newValue, render)
                click.player.sendSettingChanged("Du hast Scroll Sounds nun ", newValue)
                click.playClickSound()
            }
            watch(scroll)
        }

        render.slot(3, 7) {
            renderWith { scoreboardItem(scoreboard[render]) }
            onClick { click ->
                val newValue = !scoreboard[render]
                scoreboard.set(newValue, render)
                click.player.sendSettingChanged("Du hast Scoreboards nun ", newValue)
                click.playClickSound()
            }
            watch(scoreboard)
        }

        render.slot(5, 5) {
            withItem(menuItem(Material.BARRIER, "Zurück"))
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(SettingsMenu)
            }
        }
    }

    override fun onViewClose(close: CloseContext) {
        val player = close.player
        SettingsPlatform.launchAsync {
            if (initialScroll[close] != scroll[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.LOBBY_SCROLL_SOUND,
                    scroll[close]
                )
            }
            if (initialNametag[close] != nametag[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.SHOW_NAMETAGS,
                    nametag[close]
                )
            }
            if (initialScoreboard[close] != scoreboard[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.SHOW_SCOREBOARD,
                    scoreboard[close]
                )
            }
        }
    }
}

private fun lobbyScrollItem(state: Boolean) = settingItem(
    Material.STONE_BUTTON,
    "Lobby Hotbar Scroll Sounds",
    "Passe deine Lobby Einstellungen an.",
    state
)

private fun nameTagItem(state: Boolean) = settingItem(
    Material.NAME_TAG,
    "Nametag Sichtbarkeit",
    "Passe die Nametag Sichtbarkeit an.",
    state,
    listOf(
        "In einigen Fällen kann diese Einstellung",
        "vom Server überschrieben werden."
    )
)

private fun scoreboardItem(state: Boolean) = settingItem(
    Material.WRITABLE_BOOK,
    "Scoreboard Sichtbarkeit",
    "Passe die Scoreboard Sichtbarkeit an.",
    state,
    listOf(
        "In einigen Fällen kann diese Einstellung",
        "vom Server überschrieben werden."
    )
)
