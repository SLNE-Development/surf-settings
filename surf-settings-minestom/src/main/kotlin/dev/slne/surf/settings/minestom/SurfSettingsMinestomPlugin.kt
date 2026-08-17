package dev.slne.surf.settings.minestom

import com.google.auto.service.AutoService
import dev.slne.minestom.lobby.api.plugin.MinestomPlugin
import dev.slne.minestom.lobby.api.plugin.annotation.MinestomPluginMeta
import dev.slne.surf.settings.minestom.command.SettingsCommandRegistrar
import dev.slne.surf.settings.minestom.listener.PlayerConnectionListener

@AutoService(MinestomPlugin::class)
@MinestomPluginMeta(
    "surf-settings-minestom",
    dependsOn = ["surf-api-minestom", "surf-rabbitmq-minestom"]
)
class SurfSettingsMinestomPlugin :
    MinestomPlugin(SurfSettingsMinestomEntrypoint::class.java) {
    override fun configurePlugin() {
        bindEventRegistrar<PlayerConnectionListener>()
        bindCommandRegistrar<SettingsCommandRegistrar>()
    }
}
