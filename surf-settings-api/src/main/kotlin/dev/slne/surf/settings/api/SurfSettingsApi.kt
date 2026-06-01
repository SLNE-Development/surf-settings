package dev.slne.surf.settings.api

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.api.setting.SettingKey
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

private val api = requiredService<SurfSettingsApi>()

interface SurfSettingsApi {
    /**
     * Gets the value of a setting for a player using a typesafe [SettingKey].
     * Returns the [SettingKey.defaultValue] if the player has no value set.
     */
    fun <T : Any> getSettingValue(playerUuid: UUID, key: SettingKey<T>): T

    /**
     * Gets the value of a setting for an offline player using a typesafe [SettingKey].
     * Returns the cached value, or loaded from the database or the [SettingKey.defaultValue] if the player has no value set.
     */
    suspend fun <T : Any> getCachedValueOrLoad(playerUuid: UUID, key: SettingKey<T>): T

    /**
     * Saves a typesafe setting value for a player.
     */
    suspend fun <T : Any> saveSetting(playerUuid: UUID, key: SettingKey<T>, value: T)

    /**
     * Creates a setting from a typesafe [SettingKey], using its default value.
     */
    suspend fun <T : Any> createSetting(key: SettingKey<T>): Setting

    /**
     * Gets the [Setting] definition for a typesafe [SettingKey].
     */
    fun <T : Any> getSetting(key: SettingKey<T>): Setting?

    /**
     * Gets all registered settings.
     */
    fun getSettings(): ObjectSet<Setting>

    /**
     * Opens the settings GUI for a player.
     */
    fun openSettingsGui(playerUuid: UUID)

    @Deprecated(
        "Use getSettingValue(playerUuid, key) instead.",
        ReplaceWith("getSettingValue(playerUuid, key)")
    )
    fun getPlayerSettings(playerUuid: UUID): ObjectSet<PlayerSetting>

    @Deprecated(
        "Use getSettingValue(playerUuid, key) instead.",
        ReplaceWith("getSettingValue(playerUuid, key)")
    )
    fun getPlayerSetting(playerUuid: UUID, settingName: String): PlayerSetting?

    @Deprecated("Use createSetting(key) instead.", ReplaceWith("createSetting(key)"))
    suspend fun createSetting(name: String, defaultValue: String): Setting

    @Deprecated(
        "Use saveSetting(playerUuid, key, value) instead.",
        ReplaceWith("saveSetting(playerUuid, key, value)")
    )
    suspend fun saveSetting(playerUuid: UUID, playerSetting: PlayerSetting)

    @Deprecated(
        "Use saveSetting(playerUuid, key, value) instead.",
        ReplaceWith("saveSetting(playerUuid, key, value)")
    )
    suspend fun saveSetting(playerUuid: UUID, settingName: String, settingValue: String)

    @Deprecated("Use getSetting(key) instead.", ReplaceWith("getSetting(key)"))
    fun getSetting(name: String): Setting?

    companion object : SurfSettingsApi by api
}