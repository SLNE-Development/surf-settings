package dev.slne.surf.settings.paper

import dev.slne.surf.cloud.api.common.CloudInstance
import dev.slne.surf.cloud.api.common.startSpringApplication
import dev.slne.surf.settings.SurfSettingsApplication
import dev.slne.surf.settings.core.ContextHolderImpl
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap

@Suppress("UnstableApiUsage")
class PaperBootstrap : PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        ContextHolderImpl.instance.context =
            CloudInstance.startSpringApplication(SurfSettingsApplication::class)
    }
}