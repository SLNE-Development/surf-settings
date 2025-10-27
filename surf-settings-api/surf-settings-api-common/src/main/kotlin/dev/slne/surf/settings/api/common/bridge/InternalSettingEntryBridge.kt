package dev.slne.surf.settings.api.common.bridge

import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.beans.factory.getBean
import java.util.*

@InternalSettingsApi
interface InternalSettingEntryBridge {
    suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean

    suspend fun reset(playerUUID: UUID, setting: Setting): Boolean
    suspend fun getAll(playerUuid: UUID): ObjectSet<SettingEntry>
    suspend fun getAll(): ObjectSet<SettingEntry>
    suspend fun getEntry(playerUUID: UUID, setting: Setting): SettingEntry?

    companion object {
        val instance get() = InternalSettingsContextHolder.instance.context.getBean<InternalSettingEntryBridge>()
    }
}

@OptIn(InternalSettingsApi::class)
val settingsEntryBridge get() = InternalSettingEntryBridge.instance