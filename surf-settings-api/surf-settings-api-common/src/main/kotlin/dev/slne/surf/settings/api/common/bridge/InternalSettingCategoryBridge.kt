package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.result.category.SettingCategoryCreateResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryDeleteResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryQueryResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.beans.factory.getBean

@InternalSettingsApi
interface InternalSettingCategoryBridge {
    suspend fun createCategory(category: SettingCategory): SettingCategoryCreateResult
    suspend fun deleteCategory(category: SettingCategory): SettingCategoryDeleteResult
    suspend fun queryCategory(identifier: String): SettingCategoryQueryResult

    suspend fun queryAll(): ObjectSet<SettingCategory>

    val descriptor: SerialDescriptor
    fun serialize(encoder: Encoder, value: SettingCategory)
    fun deserialize(decoder: Decoder): SettingCategory

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingCategoryBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingCategoryBridge get() = InternalSettingCategoryBridge.instance