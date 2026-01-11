package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet

val settingsService = requiredService<SettingsService>()

interface SettingsService {
    val settings: ObjectSet<Setting>

    fun getSettingByName(name: String): Setting?

    suspend fun refreshSettings()
    suspend fun createSetting(name: String, defaultValue: String): Setting
    suspend fun deleteSetting(name: String)
}