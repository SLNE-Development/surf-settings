package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.settings.api.surfSettingsApi
import dev.slne.surf.settings.core.common.service.settingsService
import dev.slne.surf.settings.core.paper.PaperSettingsInstance
import dev.slne.surf.settings.paper.command.settingsCommand
import dev.slne.surf.settings.paper.command.surfSettingsCommand
import dev.slne.surf.settings.paper.listener.PlayerConnectionListener
import dev.slne.surf.surfapi.bukkit.api.event.register
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        PaperSettingsInstance.paperLoader.onLoad()
    }

    override suspend fun onEnableAsync() {
        PaperSettingsInstance.paperLoader.onEnable()
        PlayerConnectionListener.register()

        settingsService.refreshSettings()

        surfSettingsCommand()
        settingsCommand()

        // @formatter:off
        surfSettingsApi.createSetting("chat_pings", true.toString())
        surfSettingsApi.createSetting("chat_deathmessages", true.toString())
        surfSettingsApi.createSetting("direct_messages", true.toString())
        surfSettingsApi.createSetting("lobby_scroll_sound", false.toString())
        surfSettingsApi.createSetting("clan_invites", true.toString())
        surfSettingsApi.createSetting("clan_chat_messages", true.toString())
        surfSettingsApi.createSetting("friend_requests", true.toString())
        surfSettingsApi.createSetting("friend_jumps", true.toString())

        // @formatter:on
    }

    override suspend fun onDisableAsync() {
        PaperSettingsInstance.paperLoader.onDisable()
    }

    fun isFolia(): Boolean = runCatching {
        Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    }.isSuccess
}