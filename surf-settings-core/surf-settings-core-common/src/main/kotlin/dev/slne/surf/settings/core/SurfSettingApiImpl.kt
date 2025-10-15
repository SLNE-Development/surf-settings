package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.SurfSettingsApi
import dev.slne.surf.settings.api.common.bridge.settingsBridge
import dev.slne.surf.settings.api.common.bridge.settingsEntryBridge
import dev.slne.surf.settings.api.common.result.setting.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.impl.SettingImpl
import dev.slne.surf.surfapi.core.api.util.random
import org.springframework.stereotype.Service
import java.util.*

@OptIn(InternalSettingsApi::class)
@Service
class SurfSettingApiImpl : SurfSettingsApi {
    override suspend fun createSetting(
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): SettingCreateResult = settingsBridge.createSetting(
        SettingImpl(
            random.nextLong(),
            identifier,
            category,
            displayName,
            description,
            defaultValue
        )
    )

    override suspend fun createSettingIfNotExists(
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ): SettingCreateIgnoringResult =
        settingsBridge.createIfNotExists(
            SettingImpl(
                random.nextLong(),
                identifier,
                category,
                displayName,
                description,
                defaultValue
            )
        )

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