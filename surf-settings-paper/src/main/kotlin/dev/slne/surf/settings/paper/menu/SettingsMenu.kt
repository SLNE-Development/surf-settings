package dev.slne.surf.settings.paper.menu

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIcon
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.plugin
import me.devnatan.inventoryframework.context.Context
import net.kyori.adventure.text.format.TextDecoration

val settingsView = paginatedSurfView("Einstellungen") {
    val initialValues = mutableMapOf<Setting, Boolean>()
    val changedValues = mutableMapOf<Setting, Boolean>()

    fun values(context: Context) =
        SettingsService.getLoadedSettingsWithDefaults(context.player.uniqueId)
            .filter { it.setting.defaultValue.toBooleanStrictOrNull() != null }

    settings {
        paginationViewRows(PaginationViewRows.THREE)
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

            renderWith {
                val currentValue = changedValues[playerSetting.setting]
                    ?: initialValues[playerSetting.setting]
                    ?: playerSetting.getBoolean()

                buildItem(display.material) {
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
                val currentValue = changedValues[playerSetting.setting]
                    ?: initialValues[playerSetting.setting]
                    ?: playerSetting.getBoolean()

                val newValue = !currentValue
                changedValues[playerSetting.setting] = newValue

                click.update()
            }
        }
    }

    onFirstRender {
        initialValues.clear()
        initialValues.putAll(values(this).associate { it.setting to it.getBoolean() })
    }

    onClose {
        val playerUuid = this.player.uniqueId

        plugin.launch {
            changedValues.forEach { (setting, newValue) ->
                SettingsService.savePlayerSetting(
                    playerUuid, PlayerSetting(
                        setting, newValue.toString()
                    )
                )
            }
        }
    }
}