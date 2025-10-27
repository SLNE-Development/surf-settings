package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean

@InternalSettingsApi
interface InternalSettingBridge {
    suspend fun createSetting(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting?

    suspend fun delete(identifier: String): Boolean
    suspend fun getSetting(identifier: String): Setting?
    suspend fun all(): ObjectSet<Setting>

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingsBridge get() = InternalSettingBridge.instance