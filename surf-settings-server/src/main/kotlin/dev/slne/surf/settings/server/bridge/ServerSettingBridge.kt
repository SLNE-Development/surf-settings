package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.bridge.InternalSettingBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.server.repository.SettingRepository
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ServerSettingBridge(
    private val settingRepository: SettingRepository
) : InternalSettingBridge {
    override suspend fun createSetting(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ) = settingRepository.createSetting(
        identifier,
        category,
        displayName,
        description,
        defaultValue
    )

    override suspend fun delete(identifier: String) = settingRepository.delete(identifier)
    override suspend fun getSetting(identifier: String) = settingRepository.getSetting(identifier)
    override suspend fun all() = settingRepository.all()

}