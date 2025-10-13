package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.result.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.SettingCreateResult
import dev.slne.surf.settings.api.common.result.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.SettingQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryResetResult
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

interface SurfSettingsApi {
    suspend fun createSetting(setting: Setting): SettingCreateResult
    suspend fun createSettingIfNotExists(setting: Setting): SettingCreateIgnoringResult
    suspend fun deleteSetting(identifier: String): SettingDeleteResult
    suspend fun querySetting(identifier: String): SettingQueryResult
    suspend fun queryAllSettings(): ObjectSet<Setting>
    suspend fun querySettingByCategory(category: String): ObjectSet<Setting>

    suspend fun modifyEntry(playerUuid: UUID, settingEntry: SettingEntry): SettingEntryModifyResult
    suspend fun resetEntry(playerUuid: UUID, setting: Setting): SettingEntryResetResult
    suspend fun allEntries(playerUuid: UUID): ObjectSet<SettingEntry>
    suspend fun allEntries(): ObjectSet<SettingEntry>
    suspend fun queryEntry(playerUuid: UUID, setting: Setting): SettingEntryQueryResult

    companion object {
        @OptIn(InternalSettingsApi::class)
        val INSTANCE get() = InternalSettingsContextHolder.instance.context.getBean<SurfSettingsApi>()
    }
}

val surfSettingApi get() = SurfSettingsApi.INSTANCE