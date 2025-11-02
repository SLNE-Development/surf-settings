package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.SurfSettingsApi
import dev.slne.surf.settings.api.common.bridge.settingsBridge
import dev.slne.surf.settings.api.common.bridge.settingsEntryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.impl.SettingEntryImpl
import dev.slne.surf.settings.core.impl.SettingImpl
import dev.slne.surf.surfapi.core.api.util.toMutableObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Service
import java.util.*

@OptIn(InternalSettingsApi::class)
@Service
class SurfSettingApiImpl : SurfSettingsApi {
    override suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ) = settingsBridge.createSetting(
        uid,
        identifier,
        displayName,
        description,
        category,
        defaultValue
    )

    override suspend fun deleteSetting(uid: UUID) = settingsBridge.delete(uid)
    override suspend fun getSetting(uid: UUID) = settingsBridge.getSetting(uid)
    override suspend fun getSetting(identifier: String) = settingsBridge.getSetting(identifier)

    override suspend fun getSettings() = settingsBridge.all()

    override suspend fun modifyEntry(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ) = settingsEntryBridge.modify(playerUuid, setting, value)

    override suspend fun resetEntry(
        playerUuid: UUID,
        setting: Setting
    ) = settingsEntryBridge.reset(playerUuid, setting)

    override suspend fun getEntries(playerUuid: UUID, defaults: Boolean): ObjectSet<SettingEntry> =
        settingsEntryBridge.getAll(playerUuid).let {
            val modifiedEntries = it.toMutableObjectSet()
            if (defaults) {
                val allSettings = settingsBridge.all().associateBy { assBy -> assBy.uid }
                for (setting in allSettings.values) {
                    if (modifiedEntries.none { entry -> entry.setting.uid == setting.uid }) {
                        modifiedEntries.add(
                            SettingEntryImpl(
                                setting,
                                setting.defaultValue
                            )
                        )
                    }
                }
            }
            modifiedEntries
        }


    override suspend fun getEntries(
        playerUuid: UUID,
        category: String,
        defaults: Boolean
    ): ObjectSet<SettingEntry> = settingsEntryBridge.getEntries(playerUuid, category, defaults)

    override suspend fun getCategories() = settingsBridge.getCategories()

    override suspend fun getEntries(): ObjectSet<SettingEntry> =
        settingsEntryBridge.getAll()

    override suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting,
        defaults: Boolean
    ): SettingEntry? =
        if (defaults) settingsEntryBridge.getEntry(playerUuid, setting) ?: SettingEntryImpl(
            setting,
            setting.defaultValue
        ) else settingsEntryBridge.getEntry(playerUuid, setting)

    override fun buildSetting(
        uid: UUID,
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ) = SettingImpl(
        uid = uid,
        identifier = identifier,
        category = category,
        displayName = displayName,
        description = description,
        defaultValue = defaultValue
    )
}