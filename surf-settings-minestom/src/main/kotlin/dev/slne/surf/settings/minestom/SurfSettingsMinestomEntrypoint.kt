package dev.slne.surf.settings.minestom

import com.google.inject.Inject
import com.google.inject.Singleton
import dev.slne.minestom.lobby.api.plugin.MinestomPluginEntrypoint
import dev.slne.minestom.lobby.api.plugin.annotation.DataDirectory
import dev.slne.surf.api.minestom.inventory.framework.register
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.client.createDefaultSettings
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.minestom.menu.SettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.ChatSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.ClanSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.FriendSettingsMenu
import dev.slne.surf.settings.minestom.menu.sub.OtherSettingsMenu
import java.nio.file.Path

@Singleton
class SurfSettingsMinestomEntrypoint @Inject constructor(
    @DataDirectory path: Path
) : MinestomPluginEntrypoint {
    init {
        dataPath = path
    }

    override suspend fun start() {
        ChatSettingsMenu.register()
        ClanSettingsMenu.register()
        FriendSettingsMenu.register()
        OtherSettingsMenu.register()
        SettingsMenu.register()

        ClientSettingsInstance.clientLoader.onLoad()
        ClientSettingsInstance.clientLoader.onEnable()

        SettingsService.refreshSettings()
        createDefaultSettings()
    }

    override suspend fun stop() {
        ClientSettingsInstance.clientLoader.onDisable()
    }

    companion object {
        lateinit var dataPath: Path
            private set
    }
}
