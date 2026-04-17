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
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.menu.localColored
import dev.slne.surf.settings.paper.menu.playClickSound
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.CloseContext
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

object ClanSettingsMenu : AbstractSurfView("Clansystem") {
    val invites = mutableState(false)
    val chat = mutableState(false)

    val invitesInit = mutableState(false)
    val chatInit = mutableState(false)

    override fun onViewInit(config: ViewConfigBuilder) {
        config.size(5).cancelInteractions()
    }

    override fun onViewRender(render: RenderContext) {
        val p = render.player

        val i = SurfSettingsApi.getPlayerSetting(p.uniqueId, "clan_invites")?.getBoolean() ?: true
        val c =
            SurfSettingsApi.getPlayerSetting(p.uniqueId, "clan_chat_messages")?.getBoolean() ?: true

        invites.set(i, render)
        chat.set(c, render)

        invitesInit.set(i, render)
        chatInit.set(c, render)

        render.slot(3, 3) {
            renderWith { clanInvitesItem(invites[render]) }
            onClick { click ->
                val n = !invites[render]
                invites.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Clan Einladungen nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(invites)
        }

        render.slot(3, 7) {
            renderWith { clanChatItem(chat[render]) }
            onClick { click ->
                val n = !chat[render]
                chat.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Clan-Chat nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(chat)
        }

        render.slot(5, 5) {
            withItem(buildItem(Material.BARRIER) {
                displayName { localColored("Zurück".toSmallCaps(), TextDecoration.BOLD) }
            })
            onClick { click ->
                click.playClickSound()
                click.openForPlayer(SettingsMenu)
            }
        }
    }

    override fun onViewClose(close: CloseContext) {
        val p = close.player
        plugin.launch {
            if (invitesInit[close] != invites[close])
                SurfSettingsApi.saveSetting(p.uniqueId, "clan_invites", invites[close].toString())
            if (chatInit[close] != chat[close])
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    "clan_chat_messages",
                    chat[close].toString()
                )
        }
    }
}

private fun clanInvitesItem(state: Boolean) = buildItem(Material.FIREWORK_ROCKET) {
    displayName { localColored("Clan Einladungen".toSmallCaps(), TextDecoration.BOLD) }
    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Clan Einstellungen an.") }
        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}

private fun clanChatItem(state: Boolean) = buildItem(Material.CLOCK) {
    displayName { localColored("Clan Chat".toSmallCaps(), TextDecoration.BOLD) }
    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Clan Einstellungen an.") }
        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}