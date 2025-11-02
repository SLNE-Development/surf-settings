package dev.slne.surf.settings.server

import dev.slne.surf.cloud.api.common.CloudInstance
import dev.slne.surf.cloud.api.common.startSpringApplication
import dev.slne.surf.cloud.api.server.plugin.bootstrap.BootstrapContext
import dev.slne.surf.cloud.api.server.plugin.bootstrap.StandalonePluginBootstrap
import dev.slne.surf.settings.SurfSettingsApplication
import dev.slne.surf.settings.core.SettingsContextHolderImpl

class PluginBootstrap : StandalonePluginBootstrap {
    override suspend fun bootstrap(context: BootstrapContext) {
        SettingsContextHolderImpl.instance.context =
            CloudInstance.startSpringApplication(SurfSettingsApplication::class)
    }
}