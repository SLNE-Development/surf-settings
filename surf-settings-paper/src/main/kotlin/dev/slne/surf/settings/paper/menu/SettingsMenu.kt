package dev.slne.surf.settings.paper.menu

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemRender
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIcon
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.paper.inventory.framework.view.layoutTarget
import dev.slne.surf.api.paper.inventory.framework.view.onClose
import dev.slne.surf.api.paper.inventory.framework.view.paginatedSurfView
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.api.paper.util.BukkitSound
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.context.Context
import net.kyori.adventure.text.format.TextDecoration
import java.util.*

private val draftSettings = mutableListOf<Pair<UUID, PlayerSetting>>()

val settingsView = paginatedSurfView("Einstellungen") {
    fun values(context: Context) =
        SettingsService.getLoadedSettingsWithDefaults(context.player.uniqueId)
            .filter { it.setting.defaultValue.toBooleanStrictOrNull() != null }
            .filter { settingKeyMappings.containsKey(it.setting.name) }
            .sortedBy { it.setting.name }

    settings {
        paginationViewRows(PaginationViewRows.TWO)
        navigateBackOnOutsideClick(false)
    }

    layoutTarget('I')

    pagination {
        computedSource { context ->
            values(context)
        }

        itemFactory { playerSetting ->
            val display = settingKeyMappings[playerSetting.setting.name]

            if (display == null) {
                withItem(ViewIcon(ViewIconType.QUESTION_MARK, ViewIconColor.RED).build {
                    displayName {
                        error("Unbekannte Einstellung", TextDecoration.BOLD)
                    }

                    buildLore {
                        line {
                            spacer(playerSetting.setting.name)
                        }
                    }
                })
                return@itemFactory
            }

            onItemRender {
                val currentValue =
                    draftSettings.find { it.first == this.player.uniqueId && it.second.setting.name == playerSetting.setting.name }?.second?.getBoolean()
                        ?: playerSetting.getBoolean()

                item = buildItem(display.material) {
                    displayName {
                        white(display.displayName.toSmallCaps())
                    }

                    buildLore {
                        emptyLine()
                        line {
                            spacer(display.description)
                        }
                        emptyLine()
                        line {
                            darkSpacer("▪")
                            appendSpace()
                            variableValue("Aktiviert")
                            if (currentValue) decorate(TextDecoration.BOLD)
                        }
                        line {
                            darkSpacer("▪")
                            appendSpace()
                            variableValue("Deaktiviert")
                            if (!currentValue) decorate(TextDecoration.BOLD)
                        }
                    }
                }
            }
            onClick { click ->
                val currentValue =
                    draftSettings.find { it.first == click.player.uniqueId && it.second.setting.name == playerSetting.setting.name }?.second?.getBoolean()
                        ?: playerSetting.getBoolean()

                val newValue = !currentValue

                draftSettings.removeIf { it.first == click.player.uniqueId && it.second.setting.name == playerSetting.setting.name }
                draftSettings.add(
                    click.player.uniqueId to PlayerSetting(
                        playerSetting.setting,
                        newValue.toString()
                    )
                )

                click.player.playSound(true) {
                    type(BukkitSound.UI_BUTTON_CLICK)
                }

                click.update()
            }
        }
    }

    onClose {
        val playerUuid = this.player.uniqueId

        plugin.launch {
            draftSettings.filter { it.first == playerUuid }.forEach { (player, setting) ->
                draftSettings.removeIf { it.first == playerUuid && it.second.setting.name == setting.setting.name }
                SettingsService.cachePlayerSetting(playerUuid, setting)
                SettingsService.savePlayerSetting(
                    playerUuid, setting
                )
            }
        }
    }
}