package dev.slne.surf.settings.core

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.SurfSettingsApi
import dev.slne.surf.settings.api.common.bridge.settingCategoryBridge
import dev.slne.surf.settings.api.common.bridge.settingsBridge
import dev.slne.surf.settings.api.common.bridge.settingsEntryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.impl.SettingCategoryImpl
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
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ) = settingsBridge.createSetting(identifier, category, displayName, description, defaultValue)

    override suspend fun deleteSetting(identifier: String) = settingsBridge.delete(identifier)
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
                val allSettings = settingsBridge.all().associateBy { assBy -> assBy.identifier }
                for (setting in allSettings.values) {
                    if (modifiedEntries.none { entry -> entry.settingIdentifier == setting.identifier }) {
                        modifiedEntries.add(
                            SettingEntryImpl(
                                setting.identifier,
                                setting.defaultValue
                            )
                        )
                    }
                }
            }
            modifiedEntries
        }

    override suspend fun getEntries(): ObjectSet<SettingEntry> =
        settingsEntryBridge.getAll()

    override suspend fun getEntry(
        playerUuid: UUID,
        setting: Setting,
        defaults: Boolean
    ): SettingEntry? =
        if (defaults) settingsEntryBridge.getEntry(playerUuid, setting) ?: SettingEntryImpl(
            setting.identifier,
            setting.defaultValue
        ) else settingsEntryBridge.getEntry(playerUuid, setting)

    override suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ) = settingCategoryBridge.createCategory(identifier, displayName, description)

    override suspend fun getCategories() = settingCategoryBridge.all()
    override suspend fun getCategory(identifier: String) =
        settingCategoryBridge.getCategory(identifier)

    override suspend fun deleteCategory(category: SettingCategory) =
        settingCategoryBridge.deleteCategory(category)

    override fun buildSetting(
        identifier: String,
        category: String,
        displayName: String,
        description: String,
        defaultValue: String
    ) = SettingImpl(
        identifier = identifier,
        category = category,
        displayName = displayName,
        description = description,
        defaultValue = defaultValue
    )

    override fun buildCategory(
        identifier: String,
        displayName: String,
        description: String
    ) = SettingCategoryImpl(
        identifier = identifier,
        displayName = displayName,
        description = description
    )
}