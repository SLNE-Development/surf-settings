package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

interface SurfSettingsApi {
    suspend fun createSetting(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting?

    suspend fun deleteSetting(identifier: String): Boolean
    suspend fun getSetting(identifier: String): Setting?
    suspend fun getSettings(): ObjectSet<Setting>

    suspend fun modifyEntry(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean

    suspend fun resetEntry(playerUuid: UUID, setting: Setting): Boolean
    suspend fun getEntries(playerUuid: UUID, defaults: Boolean = true): ObjectSet<SettingEntry>
    suspend fun getEntries(): ObjectSet<SettingEntry>
    suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting,
        defaults: Boolean = true
    ): SettingEntry?

    suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategory?

    suspend fun getCategories(): ObjectSet<SettingCategory>
    suspend fun getCategory(identifier: String): SettingCategory?
    suspend fun deleteCategory(category: SettingCategory): Boolean

    fun buildSetting(
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting

    fun buildCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategory

    companion object {
        @OptIn(InternalSettingsApi::class)
        val INSTANCE get() = InternalSettingsContextHolder.instance.context.getBean<SurfSettingsApi>()
    }
}

val surfSettingApi get() = SurfSettingsApi.INSTANCE