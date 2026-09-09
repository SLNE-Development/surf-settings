package dev.slne.surf.settings.minestom.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.adventure.key
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.minestom.builder.buildItem
import dev.slne.surf.api.minestom.inventory.framework.dsl.onItemRender
import dev.slne.surf.api.minestom.inventory.framework.view.icon.ViewIcon
import dev.slne.surf.api.minestom.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.minestom.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.minestom.inventory.framework.view.layoutTarget
import dev.slne.surf.api.minestom.inventory.framework.view.onClose
import dev.slne.surf.api.minestom.inventory.framework.view.paginatedSurfView
import dev.slne.surf.api.minestom.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.minestom.inventory.framework.view.settings
import dev.slne.surf.api.minestom.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.core.common.service.SettingsService
import me.devnatan.inventoryframework.context.Context
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import java.util.*

private val draftSettings = mutableListOf<Pair<UUID, PlayerSetting>>()

val settingsView = paginatedSurfView("Einstellungen") {
    fun values(context: Context) =
        SettingsService.getLoadedSettingsWithDefaults(context.player.uuid)
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

                    lore {
                        spacer(playerSetting.setting.name)
                    }
                })
                return@itemFactory
            }

            onItemRender {
                val currentValue =
                    draftSettings.find { it.first == this.player.uuid && it.second.setting.name == playerSetting.setting.name }?.second?.getBoolean()
                        ?: playerSetting.getBoolean()

                item = buildItem(display.material) {
                    displayName {
                        white(display.displayName.toSmallCaps())
                    }

                    val lines = listOf(
                        Component.empty(),
                        buildText {
                            spacer(display.description)
                        },
                        Component.empty(),
                        buildText {
                            darkSpacer("▪")
                            appendSpace()
                            variableValue("Aktiviert")
                            if (currentValue) decorate(TextDecoration.BOLD)
                        },
                        buildText {
                            darkSpacer("▪")
                            appendSpace()
                            variableValue("Deaktiviert")
                            if (!currentValue) decorate(TextDecoration.BOLD)
                        }
                    ).map { it.decoration(TextDecoration.ITALIC, false) }

                    lore(*lines.toTypedArray())
                }
            }
            onClick { click ->
                val currentValue =
                    draftSettings.find { it.first == click.player.uuid && it.second.setting.name == playerSetting.setting.name }?.second?.getBoolean()
                        ?: playerSetting.getBoolean()

                val newValue = !currentValue

                draftSettings.removeIf { it.first == click.player.uuid && it.second.setting.name == playerSetting.setting.name }
                draftSettings.add(
                    click.player.uuid to PlayerSetting(
                        playerSetting.setting,
                        newValue.toString()
                    )
                )

                click.player.playSound(true) {
                    type(key("minecraft", "ui.button.click"))
                }

                click.update()
            }
        }
    }

    onClose {
        val playerUuid = this.player.uuid

        SettingsPlatform.launchAsync {
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