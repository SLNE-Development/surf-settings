package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.bridge.InternalSettingEntryBridge
import dev.slne.surf.settings.api.common.bridge.settingsEntryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.server.repository.SettingEntryRepository
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ServerSettingEntryBridge(
    private val settingEntryRepository: SettingEntryRepository
) : InternalSettingEntryBridge {
    override suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ) = settingEntryRepository.modify(
        playerUuid,
        setting,
        value
    )

    override suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ) = settingEntryRepository.reset(
        playerUUID,
        setting
    )

    override suspend fun getAll(playerUuid: UUID) = settingEntryRepository.getAll(playerUuid)
    override suspend fun getAll() = settingEntryRepository.getAll()
    override suspend fun getEntry(
        playerUUID: UUID,
        setting: Setting
    ) = settingEntryRepository.getEntry(
        playerUUID,
        setting
    )

    override suspend fun getEntries(
        playerUuid: UUID,
        category: String,
        defaults: Boolean
    ) = settingsEntryBridge.getEntries(playerUuid, category, defaults)
}