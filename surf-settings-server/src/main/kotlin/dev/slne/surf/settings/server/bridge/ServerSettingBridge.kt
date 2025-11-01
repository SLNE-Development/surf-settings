package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.bridge.InternalSettingBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.server.repository.SettingRepository
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ServerSettingBridge(
    private val settingRepository: SettingRepository
) : InternalSettingBridge {
    override suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ) = settingRepository.createSetting(
        uid,
        identifier,
        displayName,
        description,
        category,
        defaultValue
    )

    override suspend fun getCategories() = settingRepository.getCategories()
    override suspend fun delete(uid: UUID) = settingRepository.delete(uid)
    override suspend fun getSetting(uid: UUID) = settingRepository.getSetting(uid)
    override suspend fun getSetting(identifier: String) = settingRepository.getSetting(identifier)

    override suspend fun all() = settingRepository.all()

}