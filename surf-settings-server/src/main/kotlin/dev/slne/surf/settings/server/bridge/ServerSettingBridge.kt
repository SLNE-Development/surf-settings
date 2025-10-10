package dev.slne.surf.settings.server.bridge

import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.setting.CommonSettingBridge
import dev.slne.surf.settings.server.service.SettingService
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ServerSettingBridge(
    private val settingService: SettingService
) : CommonSettingBridge() {
    override suspend fun createSetting(setting: Setting) =
        settingService.createSetting(setting)

    override suspend fun createIfNotExists(setting: Setting) =
        settingService.createIfNotExists(setting)

    override suspend fun delete(identifier: String) = settingService.delete(identifier)
    override suspend fun query(identifier: String) = settingService.query(identifier)
    override suspend fun queryAll() = settingService.queryAll()
    override suspend fun queryByCategory(category: String) =
        settingService.queryByCategory(category)
}