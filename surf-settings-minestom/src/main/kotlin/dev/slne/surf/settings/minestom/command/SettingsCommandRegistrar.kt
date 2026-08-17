package dev.slne.surf.settings.minestom.command

import com.google.inject.Singleton
import dev.slne.minestom.lobby.api.command.CommandRegistrar

@Singleton
class SettingsCommandRegistrar : CommandRegistrar {
    override fun register() {
        settingsCommand()
        surfSettingsCommand()
    }
}
