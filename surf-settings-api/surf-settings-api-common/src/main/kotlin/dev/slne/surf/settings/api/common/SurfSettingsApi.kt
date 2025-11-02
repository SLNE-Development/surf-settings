package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

/**
 * API for managing system settings and their entries.
 * Provides functionality for creating, retrieving, modifying, and organizing settings
 * and their respective entries. Settings are categorized into groups and include
 * metadata such as unique identifiers, display names, descriptions, and default values.
 */
interface SurfSettingsApi {
    /**
     * Creates a new setting with the specified parameters.
     *
     * @param uid A unique identifier for the setting.
     * @param identifier A unique textual identifier for the setting.
     * @param displayName The human-readable name of the setting.
     * @param description A detailed explanation or additional information about the setting.
     * @param category The classification or grouping for this specific setting.
     * @param defaultValue The default value associated with this setting.
     * @return The created `Setting` object if successful, or `null` if the creation fails.
     */
    suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ): Setting?

    /**
     * Deletes a setting identified by its unique identifier.
     *
     * @param uid The unique identifier (UUID) of the setting to be deleted.
     * @return `true` if the setting was successfully deleted, `false` otherwise.
     */
    suspend fun deleteSetting(uid: UUID): Boolean

    /**
     * Retrieves a specific setting by its unique identifier (UUID).
     *
     * @param uid The unique identifier of the setting to be retrieved.
     * @return The `Setting` object associated with the given UUID, or `null` if no such setting exists.
     */
    suspend fun getSetting(uid: UUID): Setting?

    /**
     * Retrieves a `Setting` object based on its unique textual identifier.
     *
     * @param identifier A unique textual identifier for the setting to be retrieved.
     * @return The `Setting` object matching the given identifier, or `null` if no such setting exists.
     */
    suspend fun getSetting(identifier: String): Setting?

    /**
     * Retrieves all available settings within the system.
     *
     * @return a collection of settings, where each setting contains metadata such as
     * its unique identifier, display name, description, category, and default value.
     */
    suspend fun getSettings(): ObjectSet<Setting>

    /**
     * Modifies the value associated with a specific player's setting entry.
     *
     * @param playerUuid The unique identifier (UUID) of the player whose setting entry is to be modified.
     * @param setting The `Setting` object representing the specific setting to be updated.
     * @param value The new value to associate with the specified setting for the player.
     * @return `true` if the modification was successful, `false` otherwise.
     */
    suspend fun modifyEntry(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean

    /**
     * Resets the value of a specific setting entry for a given player to its default value.
     *
     * @param playerUuid The unique identifier of the player whose setting entry should be reset.
     * @param setting The setting configuration that identifies the specific entry to be reset.
     * @return `true` if the entry was successfully reset to its default value, `false` otherwise.
     */
    suspend fun resetEntry(playerUuid: UUID, setting: Setting): Boolean

    /**
     * Retrieves a set of `SettingEntry` objects associated with the given player's UUID.
     * Optionally, includes the default settings if the `defaults` parameter is set to true.
     *
     * @param playerUuid The unique identifier of the player whose settings are being retrieved.
     * @param defaults A boolean indicating whether to include default settings in the result. Defaults to true.
     * @return A set of `SettingEntry` objects representing the player's settings, including defaults if specified.
     */
    suspend fun getEntries(playerUuid: UUID, defaults: Boolean = true): ObjectSet<SettingEntry>

    /**
     * Retrieves a set of `SettingEntry` objects for a given player and category.
     *
     * @param playerUuid The unique identifier of the player for whom the setting entries are retrieved.
     * @param category The category of settings to filter the entries by.
     * @param defaults Whether to include default values for the settings in case no specific entry exists for the player. Defaults to `true`.
     * @return A set of `SettingEntry` objects representing the settings for the specified player and category.
     */
    suspend fun getEntries(
        playerUuid: UUID,
        category: String,
        defaults: Boolean = true
    ): ObjectSet<SettingEntry>

    /**
     * Retrieves all unique setting categories available in the system.
     *
     * @return A set of strings representing the categories of settings.
     */
    suspend fun getCategories(): ObjectSet<String>

    /**
     * Retrieves a collection of all setting entries.
     * Each entry contains a reference to a specific `Setting` and its associated value.
     *
     * @return A set of `SettingEntry` objects representing the current state of all settings.
     */
    suspend fun getEntries(): ObjectSet<SettingEntry>

    /**
     * Retrieves a `SettingEntry` for a specific player and setting.
     *
     * @param playerUuid The unique identifier (UUID) of the player for whom the entry is being retrieved.
     * @param setting The specific `Setting` object whose entry is to be retrieved.
     * @param defaults A flag indicating whether to return the default entry if a player-specific entry is not found.
     *                 Defaults to `true`.
     * @return The `SettingEntry` associated with the specified player and setting, or `null` if no entry is found.
     */
    suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting,
        defaults: Boolean = true
    ): SettingEntry?

    /**
     * Constructs a new `Setting` instance with the provided parameters.
     *
     * @param uid A unique identifier for the setting.
     * @param identifier A unique textual identifier for the setting.
     * @param category The classification or grouping for the setting.
     * @param displayName The human-readable name of the setting.
     * @param description A detailed explanation or additional information about the setting.
     * @param defaultValue The default value associated with the setting.
     * @return A newly created `Setting` instance.
     */
    fun buildSetting(
        uid: UUID,
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting

    /**
     * Companion object for the `SurfSettingsApi` interface.
     * Provides a centralized access point to the `SurfSettingsApi` implementation.
     */
    companion object {
        /**
         * Provides access to the singleton instance of the `SurfSettingsApi` implementation.
         * This instance is used for interacting with the settings API, which allows for the
         * creation, modification, retrieval, and deletion of configurable settings within the system.
         *
         * This property retrieves the `SurfSettingsApi` bean from the Spring application context managed
         * by the `InternalSettingsContextHolder`. As it is marked with the `InternalSettingsApi` annotation,
         * it is intended for internal use only and should not be accessed directly by external code to
         * avoid compatibility issues or unexpected behavior in future versions.
         *
         * The `SurfSettingsApi` facilitates operations such as:
         * - Managing settings (creating, modifying, and deleting).
         * - Retrieving settings and their entries.
         * - Managing categories, default values, and individual user configurations for settings.
         */
        @OptIn(InternalSettingsApi::class)
        val INSTANCE get() = InternalSettingsContextHolder.instance.context.getBean<SurfSettingsApi>()
    }
}

/**
 * Provides a direct reference to the singleton instance of the `SurfSettingsApi`.
 *
 * The `SurfSettingsApi` serves as the core interface for interacting with the surf settings system, enabling
 * the management, retrieval, and organization of system settings. This property ensures a globally accessible,
 * thread-safe mechanism to access and utilize the API's functionality across the application.
 *
 * It simplifies access by leveraging a getter that directly retrieves the `INSTANCE` from the API's implementation.
 *
 * Usage of this API should be consistent with the guidelines and expectations determined by its design and documentation,
 * ensuring compatibility within the configured system context.
 */
val surfSettingApi get() = SurfSettingsApi.INSTANCE