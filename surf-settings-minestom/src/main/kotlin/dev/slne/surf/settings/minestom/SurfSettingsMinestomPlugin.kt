package dev.slne.surf.settings.minestom

import dev.slne.surf.api.minestom.inventory.framework.register
import dev.slne.surf.api.minestom.plugin.SurfMinestomPlugin
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.client.createDefaultSettings
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.minestom.command.settingsCommand
import dev.slne.surf.settings.minestom.command.surfSettingsCommand
import dev.slne.surf.settings.minestom.listener.PlayerConnectionListener
import dev.slne.surf.settings.minestom.menu.settingsView

object SurfSettingsMinestomPlugin : SurfMinestomPlugin() {
    override suspend fun onLoad() {
        settingsView.register()

        ClientSettingsInstance.clientLoader.onLoad()
        ClientSettingsInstance.clientLoader.onEnable()

        SettingsService.refreshSettings()
        createDefaultSettings()
    }

    override suspend fun onEnable() {
        settingsCommand()
        surfSettingsCommand()

        PlayerConnectionListener.register(eventNode)
    }

    override suspend fun onDisable() {
        ClientSettingsInstance.clientLoader.onDisable()
    }
}
