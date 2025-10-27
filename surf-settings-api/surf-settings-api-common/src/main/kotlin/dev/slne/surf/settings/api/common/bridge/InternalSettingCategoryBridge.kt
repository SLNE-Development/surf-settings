package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean

@InternalSettingsApi
interface InternalSettingCategoryBridge {
    suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategory?

    suspend fun deleteCategory(category: SettingCategory): Boolean
    suspend fun getCategory(identifier: String): SettingCategory?

    suspend fun all(): ObjectSet<SettingCategory>

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingCategoryBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingCategoryBridge get() = InternalSettingCategoryBridge.instance