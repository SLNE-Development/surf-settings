package dev.slne.surf.settings.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.plugin.PluginManager
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import dev.slne.surf.cloud.api.common.CloudInstance
import dev.slne.surf.cloud.api.common.startSpringApplication
import dev.slne.surf.settings.SurfSettingsApplication
import dev.slne.surf.settings.core.SettingsContextHolderImpl
import java.nio.file.Path

class VelocityMain @Inject constructor(
    val proxy: ProxyServer,
    @param:DataDirectory val dataPath: Path,
    val pluginManager: PluginManager,
    val eventManager: EventManager,
) {

    init {
        instance = this
        SettingsContextHolderImpl.instance.context =
            CloudInstance.startSpringApplication(SurfSettingsApplication::class)
    }

    companion object {
        lateinit var instance: VelocityMain
    }
}

val plugin get() = VelocityMain.instance
val proxy get() = plugin.proxy