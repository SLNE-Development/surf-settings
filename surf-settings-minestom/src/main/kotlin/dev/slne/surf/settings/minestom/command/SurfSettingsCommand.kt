package dev.slne.surf.settings.minestom.command

import dev.slne.minestom.lobby.api.command.commandapi.dsl.anyExecutor
import dev.slne.minestom.lobby.api.command.commandapi.dsl.anyExecutorSuspend
import dev.slne.minestom.lobby.api.command.commandapi.dsl.commandTree
import dev.slne.minestom.lobby.api.command.commandapi.dsl.literalArgument
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
