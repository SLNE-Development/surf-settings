package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.client.createDefaultSettings
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.command.settingsCommand
import dev.slne.surf.settings.paper.command.surfSettingsCommand
import dev.slne.surf.settings.paper.listener.PlayerConnectionListener
import dev.slne.surf.settings.paper.menu.settingsView
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        ClientSettingsInstance.clientLoader.onLoad()

        settingsView.register()
    }

    override suspend fun onEnableAsync() {
        ClientSettingsInstance.clientLoader.onEnable()
        PlayerConnectionListener.register()

        SettingsService.refreshSettings()

        surfSettingsCommand()
        settingsCommand()

        createDefaultSettings()
    }

    override suspend fun onDisableAsync() {
        ClientSettingsInstance.clientLoader.onDisable()
    }
}
