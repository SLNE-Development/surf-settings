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
import dev.slne.surf.settings.api.setting.SettingKeys
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.menu.localColored
import dev.slne.surf.settings.paper.menu.playClickSound
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.CloseContext
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

object ChatSettingsMenu : AbstractSurfView("Chat") {
    private val pings = mutableState(false)
    private val death = mutableState(false)
    private val direct = mutableState(false)
    private val connectionMessages = mutableState(false)

    private val pingsInit = mutableState(false)
    private val deathInit = mutableState(false)
    private val directInit = mutableState(false)
    private val connectionMessagesInit = mutableState(false)

    override fun onViewInit(config: ViewConfigBuilder) {
        config.size(5).cancelInteractions()
    }

    override fun onViewRender(render: RenderContext) {
        val p = render.player

        val pv = SurfSettingsApi.getSettingValue(p.uniqueId, SettingKeys.CHAT_PINGS)
        val dv = SurfSettingsApi.getSettingValue(p.uniqueId, SettingKeys.CHAT_DEATH_MESSAGES)
        val dm = SurfSettingsApi.getSettingValue(p.uniqueId, SettingKeys.DIRECT_MESSAGES)
        val cm = SurfSettingsApi.getSettingValue(p.uniqueId, SettingKeys.CONNECTION_MESSAGES)

        pings.set(pv, render)
        death.set(dv, render)
        direct.set(dm, render)
        connectionMessages.set(cm, render)

        pingsInit.set(pv, render)
        deathInit.set(dv, render)
        directInit.set(dm, render)
        connectionMessagesInit.set(cm, render)

        render.slot(3, 2) {
            renderWith { chatPingsItem(pings[render]) }
            onClick { click ->
                val n = !pings[render]
                pings.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Chat Pings nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(pings)
        }

        render.slot(3, 4) {
            renderWith { deathMessagesItem(death[render]) }
            onClick { click ->
                val n = !death[render]
                death.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Todesnachrichten nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(death)
        }

        render.slot(3, 6) {
            renderWith { connectionMessagesItem(connectionMessages[render]) }
            onClick { click ->
                val n = !connectionMessages[render]
                connectionMessages.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Verbindungsnachrichten nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(connectionMessages)
        }

        render.slot(3, 8) {
            renderWith { directMessagesItem(direct[render]) }
            onClick { click ->
                val n = !direct[render]
                direct.set(n, render)
                click.player.sendText {
                    appendSuccessPrefix()
                    success("Du hast Direktnachrichten nun ")
                    variableValue(if (n) "aktiviert" else "deaktiviert")
                    success(".")
                }
                click.playClickSound()
            }
            watch(direct)
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
            if (pingsInit[close] != pings[close]) {
                SurfSettingsApi.saveSetting(p.uniqueId, SettingKeys.CHAT_PINGS, pings[close])
            }
            if (deathInit[close] != death[close]) {
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    SettingKeys.CHAT_DEATH_MESSAGES,
                    death[close]
                )
            }
            if (directInit[close] != direct[close]) {
                SurfSettingsApi.saveSetting(p.uniqueId, SettingKeys.DIRECT_MESSAGES, direct[close])
            }

            if (connectionMessagesInit[close] != connectionMessages[close]) {
                SurfSettingsApi.saveSetting(
                    p.uniqueId,
                    SettingKeys.CONNECTION_MESSAGES,
                    connectionMessages[close]
                )
            }
        }
    }

    private fun chatPingsItem(state: Boolean) = buildItem(Material.BELL) {
        displayName { localColored("Chat Pings".toSmallCaps(), TextDecoration.BOLD) }
        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Chat Einstellungen an.") }
            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }

    private fun connectionMessagesItem(state: Boolean) = buildItem(Material.OAK_DOOR) {
        displayName { localColored("Verbindungsnachrichten".toSmallCaps(), TextDecoration.BOLD) }
        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Chat Einstellungen an.") }
            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }

    private fun deathMessagesItem(state: Boolean) = buildItem(Material.SKELETON_SKULL) {
        displayName { localColored("Todesnachrichten".toSmallCaps(), TextDecoration.BOLD) }
        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Chat Einstellungen an.") }
            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }

    private fun directMessagesItem(state: Boolean) = buildItem(Material.RED_DYE) {
        displayName { localColored("Direktnachrichten".toSmallCaps(), TextDecoration.BOLD) }
        buildLore {
            emptyLine()
            line { variableValue("Beschreibung:".toSmallCaps()) }
            line { localColored("Passe deine Chat Einstellungen an.") }
            emptyLine()
            line { variableValue("Status:".toSmallCaps()) }
            line { spacer("-"); appendSpace(); localColored(if (state) "Aktiviert" else "Deaktiviert") }
            emptyLine()
            line { spacer("Klicke, um die Einstellung zu ändern") }
        }
    }
}