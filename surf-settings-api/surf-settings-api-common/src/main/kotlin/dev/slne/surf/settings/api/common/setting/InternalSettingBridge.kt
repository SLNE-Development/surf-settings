package dev.slne.surf.settings.api.common.setting

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.result.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.SettingCreateResult
import dev.slne.surf.settings.api.common.result.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.SettingQueryResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.beans.factory.getBean

@InternalSettingsApi
interface InternalSettingBridge {
    suspend fun createSetting(setting: Setting): SettingCreateResult
    suspend fun createIfNotExists(setting: Setting): SettingCreateIgnoringResult
    suspend fun delete(identifier: String): SettingDeleteResult
    suspend fun query(identifier: String): SettingQueryResult
    suspend fun queryAll(): ObjectSet<Setting>
    suspend fun queryByCategory(category: String): ObjectSet<Setting>

    val descriptor: SerialDescriptor
    fun serialize(encoder: Encoder, value: Setting)
    fun deserialize(decoder: Decoder): Setting

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingBridge>()
    }
}