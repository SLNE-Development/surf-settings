package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.serializer.SettingEntrySerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable

/**
 * Represents an entry for a specific setting within the system.
 * A `SettingEntry` consists of a reference to the `Setting` itself
 * and its associated value. This interface is used to manage and persist
 * individual setting configurations.
 *
 * This interface is marked with `InternalSettingsApi`, indicating it
 * is intended for internal use and may lead to compatibility issues if
 * improperly used.
 */
@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingEntrySerializer::class)
interface SettingEntry {
    /**
     * Represents the actual `Setting` object associated with this `SettingEntry`.
     * This property provides access to the configurable system setting that the entry is linked to.
     * The `Setting` contains metadata such as its unique identifier, display name, description, category, and default value.
     */
    val setting: Setting

    /**
     * Represents the current value associated with a configurable setting.
     * This property is mutable, allowing updates to the setting's value.
     * It is expected to hold the user-defined or system-specified value for a setting.
     * The value is serialized as part of the setting entry, combining the setting's metadata
     * and its current value for storage or transfer purposes.
     */
    var value: String
}