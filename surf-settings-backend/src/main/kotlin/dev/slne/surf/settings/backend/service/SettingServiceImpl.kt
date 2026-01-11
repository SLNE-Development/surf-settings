package dev.slne.surf.settings.backend.service

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.backend.repository.settingRepository
import dev.slne.surf.settings.core.SettingsService
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import net.kyori.adventure.util.Services

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {
    override val settings = mutableObjectSetOf<Setting>()
    override fun getSettingByName(name: String): Setting? = settings.firstOrNull { it.name == name }

    override suspend fun refreshSettings() {
        settings.clear()
        settings.addAll(settingRepository.loadSettings())
    }

    override suspend fun createSetting(
        name: String,
        defaultValue: String
    ): Setting = settingRepository.createSetting(name, defaultValue)

    override suspend fun deleteSetting(name: String) {
        settingRepository.deleteSetting(name)
    }
}