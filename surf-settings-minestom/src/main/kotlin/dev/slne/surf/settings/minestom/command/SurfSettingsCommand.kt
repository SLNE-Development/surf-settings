package dev.slne.surf.settings.minestom.command

import dev.slne.surf.api.minestom.command.dsl.anyExecutor
import dev.slne.surf.api.minestom.command.dsl.anyExecutorSuspend
import dev.slne.surf.api.minestom.command.dsl.commandTree
import dev.slne.surf.api.minestom.command.dsl.literalArgument
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages
import dev.slne.surf.settings.core.client.permission.SettingsPermissions
import dev.slne.surf.settings.core.common.service.SettingsService
import kotlin.system.measureTimeMillis

fun surfSettingsCommand() = commandTree("surfSettings") {
    withPermission(SettingsPermissions.COMMAND_SURF_SETTINGS)

    literalArgument("refresh") {
        withPermission(SettingsPermissions.COMMAND_SURF_SETTINGS_REFRESH)
        anyExecutorSuspend { executor, _ ->
            val elapsedMillis = measureTimeMillis {
                SettingsService.refreshSettings()
            }

            executor.sendMessage(
                SettingsCommandMessages.refreshed(
                    SettingsService.settings.size,
                    elapsedMillis
                )
            )
        }
    }

    literalArgument("list") {
        withPermission(SettingsPermissions.COMMAND_SURF_SETTINGS_LIST)
        anyExecutor { executor, _ ->
            if (SettingsService.settings.isEmpty()) {
                executor.sendMessage(SettingsCommandMessages.noSettings)
                return@anyExecutor
            }

            executor.sendMessage(SettingsCommandMessages.list(SettingsService.settings))
        }
    }
}
