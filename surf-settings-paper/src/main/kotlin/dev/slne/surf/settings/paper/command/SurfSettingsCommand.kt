package dev.slne.surf.settings.paper.command

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.surf.settings.core.service.settingsService
import dev.slne.surf.settings.paper.permission.PermissionRegistry
import dev.slne.surf.settings.paper.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import kotlin.system.measureTimeMillis

fun surfSettingsCommand() = commandTree("surfSettings") {
    withPermission(PermissionRegistry.COMMAND_SURF_SETTINGS)
    literalArgument("refresh") {
        withPermission(PermissionRegistry.COMMAND_SURF_SETTINGS_REFRESH)
        anyExecutor { executor, _ ->
            plugin.launch {
                val ms = measureTimeMillis {
                    settingsService.refreshSettings()
                }

                executor.sendText {
                    appendSuccessPrefix()
                    success("Es wurden alle Einstellungen neu geladen (${settingsService.settings.size} in ${ms}ms)!")
                }
            }
        }
    }

    literalArgument("list") {
        withPermission(PermissionRegistry.COMMAND_SURF_SETTINGS_LIST)
        anyExecutor { executor, _ ->
            if (settingsService.settings.isEmpty()) {
                executor.sendText {
                    appendErrorPrefix()
                    error("Es sind keine Einstellungen verfügbar.")
                }
                return@anyExecutor
            }

            executor.sendText {
                appendInfoPrefix()
                info("Verfügbare Einstellungen: ")
                variableValue(buildString {
                    settingsService.settings.forEachIndexed { index, setting ->
                        append(setting.name)
                        if (index < settingsService.settings.size - 1) {
                            append(", ")
                        }
                    }
                })
            }
        }
    }
}