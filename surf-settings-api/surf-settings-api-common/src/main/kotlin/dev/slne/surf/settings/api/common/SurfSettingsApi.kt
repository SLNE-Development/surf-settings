package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.result.category.SettingCategoryCreateResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryDeleteResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryResetResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateResult
import dev.slne.surf.settings.api.common.result.setting.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.setting.SettingQueryResult
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
    ): SettingCreateResult

    suspend fun createSettingIfNotExists(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ): SettingCreateIgnoringResult

    suspend fun deleteSetting(identifier: String): SettingDeleteResult
    suspend fun querySetting(identifier: String): SettingQueryResult
    suspend fun queryAllSettings(): ObjectSet<Setting>
    suspend fun querySettingByCategory(category: String): ObjectSet<Setting>

    suspend fun modifyEntry(playerUuid: UUID, settingEntry: SettingEntry): SettingEntryModifyResult
    suspend fun resetEntry(playerUuid: UUID, setting: Setting): SettingEntryResetResult
    suspend fun allEntries(playerUuid: UUID): ObjectSet<SettingEntry>
    suspend fun allEntries(): ObjectSet<SettingEntry>
    suspend fun queryEntry(playerUuid: UUID, setting: Setting): SettingEntryQueryResult

    suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategoryCreateResult

    suspend fun queryAllCategories(): ObjectSet<SettingCategory>
    suspend fun queryCategory(identifier: String): SettingCategoryQueryResult
    suspend fun deleteCategory(category: SettingCategory): SettingCategoryDeleteResult

    companion object {
        @OptIn(InternalSettingsApi::class)
        val INSTANCE get() = InternalSettingsContextHolder.instance.context.getBean<SurfSettingsApi>()
    }
}

val surfSettingApi get() = SurfSettingsApi.INSTANCE