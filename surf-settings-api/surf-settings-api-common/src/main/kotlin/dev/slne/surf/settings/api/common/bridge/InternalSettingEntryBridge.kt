package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryResetResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.beans.factory.getBean
import java.util.*

@InternalSettingsApi
interface InternalSettingEntryBridge {
    suspend fun modify(playerUuid: UUID, settingEntry: SettingEntry): SettingEntryModifyResult
    suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): SettingEntryModifyResult

    suspend fun reset(playerUUID: UUID, setting: Setting): SettingEntryResetResult
    suspend fun all(playerUuid: UUID): ObjectSet<SettingEntry>
    suspend fun all(): ObjectSet<SettingEntry>
    suspend fun query(playerUUID: UUID, setting: Setting): SettingEntryQueryResult

    val descriptor: SerialDescriptor
    fun serialize(encoder: Encoder, value: SettingEntry)
    fun deserialize(decoder: Decoder): SettingEntry

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingEntryBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingsEntryBridge get() = InternalSettingEntryBridge.instance