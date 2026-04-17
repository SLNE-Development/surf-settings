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

object FriendSettingsMenu : AbstractSurfView("Freundesystem") {
    val requests = mutableState(false)
    val notify = mutableState(false)
    val sounds = mutableState(false)

    val rInit = mutableState(false)
    val nInit = mutableState(false)
    val sInit = mutableState(false)

    override fun onViewInit(config: ViewConfigBuilder) {
        config.size(5).cancelInteractions()
    }

    override fun onViewRender(render: RenderContext) {
        val p = render.player

        val r = SurfSettingsApi.getPlayerSetting(p.uniqueId, "friend-request-notifications-enabled")
            ?.getBoolean() ?: true
        val n = SurfSettingsApi.getPlayerSetting(p.uniqueId, "friend-notifications-enabled")
            ?.getBoolean() ?: true
        val s = SurfSettingsApi.getPlayerSetting(p.uniqueId, "friend-sounds-enabled")?.getBoolean()
            ?: true

        requests.set(r, render)
        notify.set(n, render)
        sounds.set(s, render)

        rInit.set(r, render)
        nInit.set(n, render)
        sInit.set(s, render)

        render.slot(3, 3) {
            renderWith { friendRequestsItem(requests[render]) }
            onClick { click ->
                val v = !requests[render]
                requests.set(v, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundschaftsanfragen nun ")
                    variableValue(if (v) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(requests)
        }

        render.slot(3, 5) {
            renderWith { friendNotifyItem(notify[render]) }
            onClick { click ->
                val v = !notify[render]
                notify.set(v, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundesbenachrichtigungen nun ")
                    variableValue(if (v) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(notify)
        }

        render.slot(3, 7) {
            renderWith { friendSoundsItem(sounds[render]) }
            onClick { click ->
                val v = !sounds[render]
                sounds.set(v, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Freundesbenachrichtungstöne nun ")
                    variableValue(if (v) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(sounds)
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
            if (rInit[close] != requests[close])
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    "friend-request-notifications-enabled",
                    requests[close].toString()
                )
            if (nInit[close] != notify[close])
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    "friend-notifications-enabled",
                    notify[close].toString()
                )
            if (sInit[close] != sounds[close])
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    "friend-sounds-enabled",
                    sounds[close].toString()
                )
        }
    }
}

private fun friendRequestsItem(state: Boolean) = buildItem(Material.POPPY) {
    displayName { localColored("Freundschaftsanfragen".toSmallCaps(), TextDecoration.BOLD) }
    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Freundes Einstellungen an.") }
        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}

private fun friendNotifyItem(state: Boolean) = buildItem(Material.RABBIT_FOOT) {
    displayName { localColored("Freundesbenachrichtigungen".toSmallCaps(), TextDecoration.BOLD) }
    buildLore {
        emptyLine()
        line { variableValue("Beschreibung:".toSmallCaps()) }
        line { localColored("Passe deine Freundes Einstellungen an.") }
        emptyLine()
        line { variableValue("Status:".toSmallCaps()) }
        line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
        emptyLine()
        line { spacer("Klicke, um die Einstellung zu ändern") }
    }
}

private fun friendSoundsItem(state: Boolean) =
    buildItem(if (state) Material.LIME_CANDLE else Material.RED_CANDLE) {
        displayName {
            localColored(
                "Freundesbenachrichtungstöne".toSmallCaps(),
                TextDecoration.BOLD
            )
        }
        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Freundes Einstellungen an.") }
            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }