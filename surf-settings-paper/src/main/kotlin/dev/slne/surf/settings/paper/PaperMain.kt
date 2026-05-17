package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.SettingKeys
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
        SurfSettingsApi.createSetting(SettingKeys.CHAT_PINGS)
        SurfSettingsApi.createSetting(SettingKeys.CHAT_DEATH_MESSAGES)
        SurfSettingsApi.createSetting(SettingKeys.DIRECT_MESSAGES)
        SurfSettingsApi.createSetting(SettingKeys.CONNECTION_MESSAGES)
        SurfSettingsApi.createSetting(SettingKeys.LOBBY_SCROLL_SOUND)
        SurfSettingsApi.createSetting(SettingKeys.CLAN_INVITES)
        SurfSettingsApi.createSetting(SettingKeys.CLAN_CHAT_MESSAGES)
        SurfSettingsApi.createSetting(SettingKeys.FRIEND_REQUEST_NOTIFICATIONS)
        SurfSettingsApi.createSetting(SettingKeys.FRIEND_NOTIFICATIONS)
        SurfSettingsApi.createSetting(SettingKeys.FRIEND_SOUNDS)
        // @formatter:on
    }

    override suspend fun onDisableAsync() {
        PaperSettingsInstance.paperLoader.onDisable()
    }

    fun isFolia(): Boolean = runCatching {
        Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    }.isSuccess
}