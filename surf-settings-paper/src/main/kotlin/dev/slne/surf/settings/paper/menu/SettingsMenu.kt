package dev.slne.surf.settings.paper.menu

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
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.core.common.service.SettingsService
import net.kyori.adventure.text.format.TextDecoration
import java.util.*
import java.util.concurrent.ConcurrentHashMap

private val draftSettings = ConcurrentHashMap<UUID, ConcurrentHashMap<String, PlayerSetting>>()

val settingsView = paginatedSurfView("Einstellungen") {
    settings {
        paginationViewRows(PaginationViewRows.TWO)
        navigateBackOnOutsideClick(false)
    }

    layoutTarget('I')

    pagination {
        computedSource { context ->
            SettingsService.getLoadedSettingsWithDefaults(context.player.uniqueId)
                .filter { it.setting.defaultValue.toBooleanStrictOrNull() != null }
                .filter { settingKeyMappings.containsKey(it.setting.name) }
                .sortedBy { it.setting.name }
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
                    draftSettings[this.player.uniqueId]?.get(playerSetting.setting.name)
                        ?.getBoolean()
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
                    draftSettings[click.player.uniqueId]?.get(playerSetting.setting.name)
                        ?.getBoolean()
                        ?: playerSetting.getBoolean()

                val newValue = !currentValue

                draftSettings.compute(click.player.uniqueId) { _, settings ->
                    val updatedSettings = settings ?: ConcurrentHashMap()
                    updatedSettings[playerSetting.setting.name] = PlayerSetting(
                        playerSetting.setting,
                        newValue.toString()
                    )
                    updatedSettings
                }

                click.player.playSound(true) {
                    type(BukkitSound.UI_BUTTON_CLICK)
                }

                click.update()
            }
        }
    }

    onClose {
        val playerUuid = this.player.uniqueId
        val changedSettings = draftSettings.remove(playerUuid) ?: return@onClose

        SettingsPlatform.launchAsync {
            changedSettings.forEach { (_, setting) ->
                SettingsService.cachePlayerSetting(playerUuid, setting)
                SettingsService.savePlayerSetting(
                    playerUuid, setting
                )
            }
        }
    }
}