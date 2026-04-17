package dev.slne.surf.settings.paper.api

import com.google.auto.service.AutoService
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.menu.SettingsMenu
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(SurfSettingsApi::class)
class SurfSettingsApiImpl : SurfSettingsApi, Services.Fallback {
    override fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting> =
        SettingsService.getSettingsForPlayer(playerUuid)

    override fun getPlayerSetting(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? = SettingsService.getSettingForPlayer(playerUuid, settingName)

    override suspend fun createSetting(name: String, defaultValue: String) =
        SettingsService.createSetting(name, defaultValue)

    override suspend fun saveSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsService.savePlayerSetting(playerUuid, playerSetting)
    }

    override suspend fun saveSetting(
        playerUuid: UUID,
        settingName: String,
        settingValue: String
    ) {
        val setting = SettingsService.getSettingByName(settingName)
            ?: return
        val playerSetting = PlayerSetting(
            setting = setting,
            settingValue = settingValue
        )

        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsService.savePlayerSetting(playerUuid, playerSetting)
    }

    override fun getSetting(name: String): Setting? = SettingsService.getSettingByName(name)
    override fun getSettings(): ObjectSet<Setting> = SettingsService.settings
    override fun openSettingsGui(playerUuid: UUID) {
        Bukkit.getPlayer(playerUuid)?.let {
            SettingsMenu.open(it)
        }
    }
}