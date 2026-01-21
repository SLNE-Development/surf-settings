package dev.slne.surf.settings.core.api

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.service.settingsService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SurfSettingsApi::class)
class SurfSettingsApiImpl : SurfSettingsApi, Services.Fallback {
    override fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting> =
        settingsService.getSettingsForPlayer(playerUuid)

    override fun getPlayerSetting(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? = settingsService.getSettingForPlayer(playerUuid, settingName)

    override suspend fun createSetting(name: String, defaultValue: String) =
        settingsService.createSetting(name, defaultValue)

    override fun getSetting(name: String): Setting? = settingsService.getSettingByName(name)
    override fun getSettings(): ObjectSet<Setting> = settingsService.settings
}