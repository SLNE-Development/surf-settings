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

object ClanSettingsMenu : AbstractSurfView("Clansystem") {
    override val settings = SimpleViewSettings(
        rows = ViewRows.FIVE,
        navigateBackOnOutsideClick = false
    )

    private val invites = mutableState(false)
    private val chat = mutableState(false)
    private val invitesInit = mutableState(false)
    private val chatInit = mutableState(false)

    override fun onViewRender(render: RenderContext) {
        val player = render.player
        val invitesValue = SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.CLAN_INVITES)
        val chatValue =
            SurfSettingsApi.getSettingValue(player.uuid, SettingKeys.CLAN_CHAT_MESSAGES)

        invites.set(invitesValue, render)
        chat.set(chatValue, render)
        invitesInit.set(invitesValue, render)
        chatInit.set(chatValue, render)

        render.slot(3, 3) {
            renderWith { clanInvitesItem(invites[render]) }
            onClick { click ->
                val newValue = !invites[render]
                invites.set(newValue, render)
                click.player.sendSettingChanged("Du hast Clan Einladungen nun ", newValue)
                click.playClickSound()
            }
            watch(invites)
        }

        render.slot(3, 7) {
            renderWith { clanChatItem(chat[render]) }
            onClick { click ->
                val newValue = !chat[render]
                chat.set(newValue, render)
                click.player.sendSettingChanged("Du hast Clan-Chat nun ", newValue)
                click.playClickSound()
            }
            watch(chat)
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
            if (invitesInit[close] != invites[close]) {
                SurfSettingsApi.saveSetting(player.uuid, SettingKeys.CLAN_INVITES, invites[close])
            }
            if (chatInit[close] != chat[close]) {
                SurfSettingsApi.saveSetting(
                    player.uuid,
                    SettingKeys.CLAN_CHAT_MESSAGES,
                    chat[close]
                )
            }
        }
    }
}

private fun clanInvitesItem(state: Boolean) = settingItem(
    Material.FIREWORK_ROCKET,
    "Clan Einladungen",
    "Passe deine Clan Einstellungen an.",
    state
)

private fun clanChatItem(state: Boolean) = settingItem(
    Material.CLOCK,
    "Clan Chat",
    "Passe deine Clan Einstellungen an.",
    state
)
