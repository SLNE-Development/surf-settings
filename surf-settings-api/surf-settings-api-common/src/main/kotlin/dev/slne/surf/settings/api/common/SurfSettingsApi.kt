package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

interface SurfSettingsApi {
    suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ): Setting?

    suspend fun deleteSetting(uid: UUID): Boolean
    suspend fun getSetting(uid: UUID): Setting?
    suspend fun getSetting(identifier: String): Setting?
    suspend fun getSettings(): ObjectSet<Setting>

    suspend fun modifyEntry(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean

    suspend fun resetEntry(playerUuid: UUID, setting: Setting): Boolean
    suspend fun getEntries(playerUuid: UUID, defaults: Boolean = true): ObjectSet<SettingEntry>
    suspend fun getEntries(
        playerUuid: UUID,
        category: String,
        defaults: Boolean = true
    ): ObjectSet<SettingEntry>

    suspend fun getCategories(): ObjectSet<String>

    suspend fun getEntries(): ObjectSet<SettingEntry>
    suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting,
        defaults: Boolean = true
    ): SettingEntry?

    fun buildSetting(
        uid: UUID,
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting

    companion object {
        @OptIn(InternalSettingsApi::class)
        val INSTANCE get() = InternalSettingsContextHolder.instance.context.getBean<SurfSettingsApi>()
    }
}

val surfSettingApi get() = SurfSettingsApi.INSTANCE