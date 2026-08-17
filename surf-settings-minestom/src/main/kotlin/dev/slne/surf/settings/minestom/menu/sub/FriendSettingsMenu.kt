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

object FriendSettingsMenu : AbstractSurfView("Freundesystem") {
    override val settings = SimpleViewSettings(
        rows = ViewRows.FIVE,
        navigateBackOnOutsideClick = false
    )

    private val requests = mutableState(false)
    private val notify = mutableState(false)
    private val sounds = mutableState(false)
    private val requestsInit = mutableState(false)
    private val notifyInit = mutableState(false)
    private val soundsInit = mutableState(false)

    override fun onViewRender(render: RenderContext) {
        val player = render.player
        val requestsValue = SurfSettingsApi.getSettingValue(
            player.uuid,
            SettingKeys.FRIEND_REQUEST_NOTIFICATIONS
        )
        val notifyValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.FRIEND_NOTIFICATIONS)
        val soundsValue = SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.FRIEND_SOUNDS)

        requests.set(requestsValue, render)
        notify.set(notifyValue, render)
        sounds.set(soundsValue, render)
        requestsInit.set(requestsValue, render)
        notifyInit.set(notifyValue, render)
        soundsInit.set(soundsValue, render)

        render.slot(3, 3) {
            renderWith { friendRequestsItem(requests[render]) }
            onClick { click ->
                val newValue = !requests[render]
                requests.set(newValue, render)
                click.player.sendSettingChanged(
                    "Du hast Freundschaftsanfragen nun ",
                    newValue
                )
                click.playClickSound()
            }
            watch(requests)
        }

        render.slot(3, 5) {
            renderWith { friendNotifyItem(notify[render]) }
            onClick { click ->
                val newValue = !notify[render]
                notify.set(newValue, render)
                click.player.sendSettingChanged(
                    "Du hast Freundesbenachrichtigungen nun ",
                    newValue
                )
                click.playClickSound()
            }
            watch(notify)
        }

        render.slot(3, 7) {
            renderWith { friendSoundsItem(sounds[render]) }
            onClick { click ->
                val newValue = !sounds[render]
                sounds.set(newValue, render)
                click.player.sendSettingChanged(
                    "Du hast Freundesbenachrichtungstöne nun ",
                    newValue
                )
                click.playClickSound()
            }
            watch(sounds)
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
            if (requestsInit[close] != requests[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.FRIEND_REQUEST_NOTIFICATIONS,
                    requests[close]
                )
            }
            if (notifyInit[close] != notify[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.FRIEND_NOTIFICATIONS,
                    notify[close]
                )
            }
            if (soundsInit[close] != sounds[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.FRIEND_SOUNDS,
                    sounds[close]
                )
            }
        }
    }
}

private fun friendRequestsItem(state: Boolean) = settingItem(
    Material.POPPY,
    "Freundschaftsanfragen",
    "Passe deine Freundes Einstellungen an.",
    state
)

private fun friendNotifyItem(state: Boolean) = settingItem(
    Material.RABBIT_FOOT,
    "Freundesbenachrichtigungen",
    "Passe deine Freundes Einstellungen an.",
    state
)

private fun friendSoundsItem(state: Boolean) = settingItem(
    if (state) Material.LIME_CANDLE else Material.RED_CANDLE,
    "Freundesbenachrichtungstöne",
    "Passe deine Freundes Einstellungen an.",
    state
)
