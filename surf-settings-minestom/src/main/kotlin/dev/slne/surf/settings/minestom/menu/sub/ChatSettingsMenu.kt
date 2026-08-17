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

object ChatSettingsMenu : AbstractSurfView("Chat") {
    override val settings = SimpleViewSettings(
        rows = ViewRows.FIVE,
        navigateBackOnOutsideClick = false
    )

    private val pings = mutableState(false)
    private val death = mutableState(false)
    private val direct = mutableState(false)
    private val connectionMessages = mutableState(false)

    private val pingsInit = mutableState(false)
    private val deathInit = mutableState(false)
    private val directInit = mutableState(false)
    private val connectionMessagesInit = mutableState(false)

    override fun onViewRender(render: RenderContext) {
        val player = render.player

        val pingsValue = SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.CHAT_PINGS)
        val deathValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.CHAT_DEATH_MESSAGES)
        val directValue = SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.DIRECT_MESSAGES)
        val connectionValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.CONNECTION_MESSAGES)

        pings.set(pingsValue, render)
        death.set(deathValue, render)
        direct.set(directValue, render)
        connectionMessages.set(connectionValue, render)

        pingsInit.set(pingsValue, render)
        deathInit.set(deathValue, render)
        directInit.set(directValue, render)
        connectionMessagesInit.set(connectionValue, render)

        render.slot(3, 2) {
            renderWith { chatPingsItem(pings[render]) }
            onClick { click ->
                val newValue = !pings[render]
                pings.set(newValue, render)
                click.player.sendSettingChanged("Du hast Chat Pings nun ", newValue)
                click.playClickSound()
            }
            watch(pings)
        }

        render.slot(3, 4) {
            renderWith { deathMessagesItem(death[render]) }
            onClick { click ->
                val newValue = !death[render]
                death.set(newValue, render)
                click.player.sendSettingChanged("Du hast Todesnachrichten nun ", newValue)
                click.playClickSound()
            }
            watch(death)
        }

        render.slot(3, 6) {
            renderWith { connectionMessagesItem(connectionMessages[render]) }
            onClick { click ->
                val newValue = !connectionMessages[render]
                connectionMessages.set(newValue, render)
                click.player.sendSettingChanged(
                    "Du hast Verbindungsnachrichten nun ",
                    newValue
                )
                click.playClickSound()
            }
            watch(connectionMessages)
        }

        render.slot(3, 8) {
            renderWith { directMessagesItem(direct[render]) }
            onClick { click ->
                val newValue = !direct[render]
                direct.set(newValue, render)
                click.player.sendSettingChanged("Du hast Direktnachrichten nun ", newValue)
                click.playClickSound()
            }
            watch(direct)
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
            if (pingsInit[close] != pings[close]) {
                SurfSettingsApi.saveSetting(player.uuid, SettingKeys.CHAT_PINGS, pings[close])
            }
            if (deathInit[close] != death[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.CHAT_DEATH_MESSAGES,
                    death[close]
                )
            }
            if (directInit[close] != direct[close]) {
                SurfSettingsApi.saveSetting(player.uuid, SettingKeys.DIRECT_MESSAGES, direct[close])
            }
            if (connectionMessagesInit[close] != connectionMessages[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.CONNECTION_MESSAGES,
                    connectionMessages[close]
                )
            }
        }
    }
}

private fun chatPingsItem(state: Boolean) = settingItem(
    Material.BELL,
    "Chat Pings",
    "Passe deine Chat Einstellungen an.",
    state
)

private fun deathMessagesItem(state: Boolean) = settingItem(
    Material.SKELETON_SKULL,
    "Todesnachrichten",
    "Passe deine Chat Einstellungen an.",
    state
)

private fun connectionMessagesItem(state: Boolean) = settingItem(
    Material.OAK_DOOR,
    "Verbindungsnachrichten",
    "Passe deine Chat Einstellungen an.",
    state
)

private fun directMessagesItem(state: Boolean) = settingItem(
    Material.RED_DYE,
    "Direktnachrichten",
    "Passe deine Chat Einstellungen an.",
    state
)
