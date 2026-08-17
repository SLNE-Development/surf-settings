package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.client.createDefaultSettings
import dev.slne.surf.settings.paper.command.settingsCommand
import dev.slne.surf.settings.paper.command.surfSettingsCommand
import dev.slne.surf.settings.paper.listener.PlayerConnectionListener
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.menu.sub.ChatSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.ClanSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.FriendSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.OtherSettingsMenu
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        ClientSettingsInstance.clientLoader.onLoad()

        ChatSettingsMenu.register()
        ClanSettingsMenu.register()
        FriendSettingsMenu.register()
        OtherSettingsMenu.register()
        SettingsMenu.register()
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

    fun isFolia(): Boolean = runCatching {
        Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    }.isSuccess
}
