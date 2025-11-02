package dev.slne.surf.settings.api.common

import dev.slne.surf.settings.api.common.serializer.SettingSerializer
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Represents a configurable setting within the system.
 * A setting is identified by a unique identifier (UID) and an additional identifier string.
 * Settings have a display name, a description, a category for organization, and a default value.
 * This interface is marked with `InternalSettingsApi`, indicating that it is intended for internal use only.
 */
@OptIn(InternalSettingsApi::class)
@Serializable(with = SettingSerializer::class)
interface Setting {
    /**
     * A unique identifier for the setting.
     * The UID is used to distinguish and reference each setting independently.
     */
    val uid: UUID

    /**
     * Represents a unique textual identifier for a particular setting.
     * This identifier is used to reference and manage the specific setting
     * across the system, including serialization, deserialization, and UI handling.
     */
    val identifier: String

    /**
     * The human-readable name of a setting.
     * This value is intended to be displayed in user interfaces or human-facing documentation.
     * It provides a more descriptive and accessible name for the setting compared to technical identifiers.
     */
    val displayName: String

    /**
     * A detailed explanation or additional information about the setting.
     * Provides context and meaning for the setting, typically used
     * to inform users or developers about the purpose and usage of the setting.
     */
    val description: String

    /**
     * Represents a classification or grouping for this specific setting.
     * The category helps in organizing and managing settings efficiently,
     * particularly when displaying or retrieving settings based on their purpose
     * or application area.
     */
    val category: String

    /**
     * The default value associated with a specific setting.
     * This value is used when there is no specific entry or customization for a particular setting.
     * It defines the initial or fallback state of the setting.
     */
    val defaultValue: String
}