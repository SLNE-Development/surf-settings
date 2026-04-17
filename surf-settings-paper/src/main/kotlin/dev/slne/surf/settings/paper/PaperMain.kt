package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.core.paper.PaperSettingsInstance
import dev.slne.surf.settings.paper.command.settingsCommand
import dev.slne.surf.settings.paper.command.surfSettingsCommand
import dev.slne.surf.settings.paper.listener.PlayerConnectionListener
import dev.slne.surf.settings.paper.menu.SettingsMenu
import dev.slne.surf.settings.paper.menu.sub.ChatSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.ClanSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.FriendSettingsMenu
import dev.slne.surf.settings.paper.menu.sub.LobbySettingsMenu
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        PaperSettingsInstance.paperLoader.onLoad()

        ChatSettingsMenu.register()
        ClanSettingsMenu.register()
        FriendSettingsMenu.register()
        LobbySettingsMenu.register()
        SettingsMenu.register()
    }

    override suspend fun onEnableAsync() {
        PaperSettingsInstance.paperLoader.onEnable()
        PlayerConnectionListener.register()

        SettingsService.refreshSettings()

        surfSettingsCommand()
        settingsCommand()

        // @formatter:off
        SurfSettingsApi.createSetting("chat_pings", true.toString())
        SurfSettingsApi.createSetting("chat_deathmessages", true.toString())
        SurfSettingsApi.createSetting("direct_messages", true.toString())
        SurfSettingsApi.createSetting("lobby_scroll_sound", false.toString())
        SurfSettingsApi.createSetting("clan_invites", true.toString())
        SurfSettingsApi.createSetting("clan_chat_messages", true.toString())
        SurfSettingsApi.createSetting("friend-request-notifications-enabled", true.toString())
        SurfSettingsApi.createSetting("friend-notifications-enabled", true.toString())
        SurfSettingsApi.createSetting("friend-sounds-enabled", true.toString())

        // @formatter:on
    }

    override suspend fun onDisableAsync() {
        PaperSettingsInstance.paperLoader.onDisable()
    }

    fun isFolia(): Boolean = runCatching {
        Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    }.isSuccess
}