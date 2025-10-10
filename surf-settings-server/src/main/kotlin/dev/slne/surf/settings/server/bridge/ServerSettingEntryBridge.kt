package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.setting.entry.CommonSettingEntryBridge
import dev.slne.surf.settings.server.service.SettingEntryService
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ServerSettingEntryBridge(
    private val settingsEntryService: SettingEntryService
) : CommonSettingEntryBridge() {
    override suspend fun modify(
        playerUuid: UUID,
        settingEntry: SettingEntry
    ) = settingsEntryService.modify(playerUuid, settingEntry)

    override suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ) = settingsEntryService.reset(playerUUID, setting)

    override suspend fun all(playerUuid: UUID) = settingsEntryService.all(playerUuid)
    override suspend fun all() = settingsEntryService.all()
    override suspend fun query(
        playerUUID: UUID,
        setting: Setting
    ) = settingsEntryService.query(playerUUID, setting)
}