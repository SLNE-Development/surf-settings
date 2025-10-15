package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.bridge.CommonSettingCategoryBridge
import dev.slne.surf.settings.server.service.SettingCategoryService
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ServerSettingCategoryBridge(
    private val settingCategoryService: SettingCategoryService
) : CommonSettingCategoryBridge() {
    override suspend fun createCategory(category: SettingCategory) =
        settingCategoryService.create(category)

    override suspend fun deleteCategory(category: SettingCategory) =
        settingCategoryService.delete(category)

    override suspend fun queryCategory(identifier: String) =
        settingCategoryService.query(identifier)

    override suspend fun queryAll() = settingCategoryService.all()
}