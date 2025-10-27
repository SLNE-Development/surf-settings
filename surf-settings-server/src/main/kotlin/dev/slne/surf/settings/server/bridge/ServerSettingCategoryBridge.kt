package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.bridge.InternalSettingCategoryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.server.repository.SettingCategoryRepository
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ServerSettingCategoryBridge(
    private val settingCategoryRepository: SettingCategoryRepository
) : InternalSettingCategoryBridge {
    override suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ) = settingCategoryRepository.createCategory(identifier, displayName, description)

    override suspend fun deleteCategory(category: SettingCategory) =
        settingCategoryRepository.deleteCategory(category)

    override suspend fun getCategory(identifier: String) =
        settingCategoryRepository.getCategory(identifier)

    override suspend fun all() = settingCategoryRepository.all()
}