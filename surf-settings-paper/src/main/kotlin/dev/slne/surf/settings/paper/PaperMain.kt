package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.settings.api.surfSettingsApi
import dev.slne.surf.settings.core.database.databaseLoader
import dev.slne.surf.settings.core.service.settingsService
import dev.slne.surf.settings.paper.command.settingsCommand
import dev.slne.surf.settings.paper.command.surfSettingsCommand
import dev.slne.surf.settings.paper.listener.PlayerConnectionListener
import dev.slne.surf.surfapi.bukkit.api.event.register
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        databaseLoader.connect(plugin.dataPath)
        settingsService.refreshSettings()
    }

    override fun onEnable() {
        PlayerConnectionListener.register()

        surfSettingsCommand()
        settingsCommand()

        runBlocking {
            surfSettingsApi.createSetting("example_setting", true.toString())
            surfSettingsApi.createSetting("chat_pings", true.toString())
            surfSettingsApi.createSetting("direct_messages", true.toString())
            surfSettingsApi.createSetting("lobby_scroll_sound", false.toString())
            surfSettingsApi.createSetting("clan_invites", true.toString())
            surfSettingsApi.createSetting("clan_chat_messages", true.toString())
            surfSettingsApi.createSetting("friend_requests", true.toString())
            surfSettingsApi.createSetting("friend_jumps", true.toString())
        }
    }

    override fun onDisable() {
        databaseLoader.disconnect()
    }

    fun isFolia(): Boolean = runCatching {
        Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    }.isSuccess
}