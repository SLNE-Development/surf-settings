package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.common.SurfSettingsApi
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import dev.slne.surf.settings.api.common.setting.entry.settingsEntryBridge
import dev.slne.surf.settings.api.common.setting.settingsBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import org.springframework.stereotype.Service
import java.util.*

@OptIn(InternalSettingsApi::class)
@Service
class SurfSettingApiImpl : SurfSettingsApi {
    override suspend fun createSetting(setting: Setting) = settingsBridge.createSetting(setting)
    override suspend fun createSettingIfNotExists(setting: Setting) =
        settingsBridge.createIfNotExists(setting)

    override suspend fun deleteSetting(identifier: String) = settingsBridge.delete(identifier)
    override suspend fun querySetting(identifier: String) = settingsBridge.query(identifier)
    override suspend fun queryAllSettings() = settingsBridge.queryAll()
    override suspend fun querySettingByCategory(category: String) =
        settingsBridge.queryByCategory(category)

    override suspend fun modifyEntry(
        playerUuid: UUID,
        settingEntry: SettingEntry
    ) = settingsEntryBridge.modify(playerUuid, settingEntry)

    override suspend fun resetEntry(
        playerUuid: UUID,
        setting: Setting
    ) = settingsEntryBridge.reset(playerUuid, setting)

    override suspend fun allEntries(playerUuid: UUID) = settingsEntryBridge.all(playerUuid)
    override suspend fun allEntries() = settingsEntryBridge.all()
    override suspend fun queryEntry(
        playerUuid: UUID,
        setting: Setting
    ) = settingsEntryBridge.query(playerUuid, setting)
}