package dev.slne.surf.settings.velocity

import com.github.shynixn.mccoroutine.velocity.SuspendingPluginContainer
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.client.createDefaultSettings
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.velocity.listener.PlayerConnectionListener
import java.nio.file.Path

lateinit var plugin: VelocityMain
    private set

class VelocityMain @Inject constructor(
    @param:DataDirectory val dataPath: Path,
    val container: PluginContainer,
    val proxy: ProxyServer,
    suspendingPluginContainer: SuspendingPluginContainer,
) {
    init {
        plugin = this
        suspendingPluginContainer.initialize(this)
    }

    @Subscribe
    suspend fun onProxyInitialize(event: ProxyInitializeEvent) {
        ClientSettingsInstance.clientLoader.onLoad()
        ClientSettingsInstance.clientLoader.onEnable()

        SettingsService.refreshSettings()
        createDefaultSettings()

        proxy.eventManager.register(this, PlayerConnectionListener)
    }

    @Subscribe
    suspend fun onProxyShutdown(event: ProxyShutdownEvent) {
        ClientSettingsInstance.clientLoader.onDisable()
    }
}