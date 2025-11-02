package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

@InternalSettingsApi
interface InternalSettingBridge {
    suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ): Setting?

    suspend fun getCategories(): ObjectSet<String>

    suspend fun delete(uid: UUID): Boolean
    suspend fun getSetting(uid: UUID): Setting?
    suspend fun getSetting(identifier: String): Setting?
    suspend fun all(): ObjectSet<Setting>

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingsBridge get() = InternalSettingBridge.instance