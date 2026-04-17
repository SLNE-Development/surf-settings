package dev.slne.surf.settings.paper.api

import com.google.auto.service.AutoService
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.api.setting.SettingKey
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.paper.menu.SettingsMenu
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(SurfSettingsApi::class)
class SurfSettingsApiImpl : SurfSettingsApi, Services.Fallback {
    override fun <T : Any> getSettingValue(playerUuid: UUID, key: SettingKey<T>): T {
        val playerSetting = SettingsService.getSettingForPlayer(playerUuid, key.name)
            ?: return key.defaultValue
        return key.deserialize(playerSetting.settingValue)
    }

    override suspend fun <T : Any> saveSetting(playerUuid: UUID, key: SettingKey<T>, value: T) {
        val setting = SettingsService.getSettingByName(key.name) ?: return
        val playerSetting = PlayerSetting(
            setting = setting,
            settingValue = key.serialize(value)
        )
        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsService.savePlayerSetting(playerUuid, playerSetting)
    }

    override suspend fun <T : Any> createSetting(key: SettingKey<T>): Setting =
        SettingsService.createSetting(key.name, key.serialize(key.defaultValue))

    override fun <T : Any> getSetting(key: SettingKey<T>): Setting? =
        SettingsService.getSettingByName(key.name)

    override fun getSettings(): ObjectSet<Setting> = SettingsService.settings

    override fun openSettingsGui(playerUuid: UUID) {
        Bukkit.getPlayer(playerUuid)?.let {
            it.closeInventory()
            SettingsMenu.open(it)
        }
    }

    @Deprecated(
        "Use getSettingValue(playerUuid, key) instead.",
        ReplaceWith("getSettingValue(playerUuid, key)")
    )
    override fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting> =
        SettingsService.getSettingsForPlayer(playerUuid)

    @Deprecated(
        "Use getSettingValue(playerUuid, key) instead.",
        ReplaceWith("getSettingValue(playerUuid, key)")
    )
    override fun getPlayerSetting(playerUuid: UUID, settingName: String): PlayerSetting? =
        SettingsService.getSettingForPlayer(playerUuid, settingName)

    @Deprecated("Use createSetting(key) instead.", ReplaceWith("createSetting(key)"))
    override suspend fun createSetting(name: String, defaultValue: String): Setting =
        SettingsService.createSetting(name, defaultValue)

    @Deprecated(
        "Use saveSetting(playerUuid, key, value) instead.",
        ReplaceWith("saveSetting(playerUuid, key, value)")
    )
    override suspend fun saveSetting(playerUuid: UUID, playerSetting: PlayerSetting) {
        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsService.savePlayerSetting(playerUuid, playerSetting)
    }

    @Deprecated(
        "Use saveSetting(playerUuid, key, value) instead.",
        ReplaceWith("saveSetting(playerUuid, key, value)")
    )
    override suspend fun saveSetting(playerUuid: UUID, settingName: String, settingValue: String) {
        val setting = SettingsService.getSettingByName(settingName) ?: return
        val playerSetting = PlayerSetting(setting = setting, settingValue = settingValue)
        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsService.savePlayerSetting(playerUuid, playerSetting)
    }

    @Deprecated("Use getSetting(key) instead.", ReplaceWith("getSetting(key)"))
    override fun getSetting(name: String): Setting? = SettingsService.getSettingByName(name)
}