package dev.slne.surf.settings.api.common.dsl

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.surfSettingApi
import java.util.*

/**
 * Configures settings using the provided suspension block, allowing multiple settings
 * to be defined within the context of a settings root. The settings block executes
 * within the DSL context of `SettingsRoot`, enabling structured configuration of
 * individual settings.
 *
 * @param block A suspending lambda defining the settings to be configured within the
 * `SettingsRoot`. The lambda allows invocation of individual setting configurations.
 */
suspend fun settings(block: suspend SettingsRoot.() -> Unit) {
    SettingsRoot().block()
}

/**
 * Constructs and creates a setting by applying the given configuration block to a `SingleSettingBuilder`.
 * This allows a setting to be programmatically configured and built within the system.
 *
 * @param block A lambda with receiver that provides a `SingleSettingBuilder` to configure the properties of the setting.
 * @return The constructed `Setting` instance, or `null` if the build process fails.
 */
suspend fun setting(block: SingleSettingBuilder.() -> Unit): Setting? {
    val builder = SingleSettingBuilder().apply(block)
    return builder.build()
}

/**
 * The `SettingsRoot` class serves as an entry point for defining individual application settings.
 * It facilitates the creation of settings through a user-defined configuration block.
 */
class SettingsRoot {
    /**
     * Defines a new setting by configuring properties within a provided block.
     * The `block` allows customization of the setting through the use
     * of the `SingleSettingBuilder`.
     *
     * @param block a suspendable lambda function which configures the setting
     * using the `SingleSettingBuilder`. The configuration may include setting
     * properties such as `identifier`, `category`, `displayName`,
     * `description`, and `defaultValue`.
     */
    suspend fun setting(block: suspend SingleSettingBuilder.() -> Unit) {
        val builder = SingleSettingBuilder()
        builder.block()
        builder.build()
    }
}

/**
 * A builder class used to define and construct a single `Setting`.
 * This class aggregates properties and provides a method to create a configurable setting entity in the system.
 */
class SingleSettingBuilder {
    /**
     * Represents the unique identifier for a setting.
     * This value is used to distinctly identify a specific setting within the system
     * and ensure no collisions occur between different settings.
     */
    lateinit var identifier: String

    /**
     * Represents a grouping or classification of settings.
     * This property is used to logically organize settings under a specific category.
     * It can be helpful for managing and displaying settings in a structured manner.
     */
    var category: String = ""

    /**
     * Represents the display name of the setting being configured.
     * This value is a human-readable string intended to be shown to users in the user interface.
     */
    var displayName: String = ""

    /**
     * Represents a brief explanation or context for a specific setting.
     * This field is used to describe the purpose or functionality of the setting
     * being defined in the system. It enhances clarity and provides additional
     * information to users or developers interacting with the setting.
     */
    var description: String = ""

    /**
     * Specifies the default value for the setting being created.
     * This can represent an initial or fallback value for the setting
     * when no user-defined value is provided.
     */
    var defaultValue: String = ""

    /**
     * Builds and creates a new setting using the specified fields within the `SingleSettingBuilder` instance.
     *
     * @return the newly created `Setting` instance or `null` if the creation process fails.
     */
    suspend fun build(): Setting? {
        return surfSettingApi.createSetting(
            uid = UUID.randomUUID(),
            identifier = identifier,
            category = category,
            displayName = displayName,
            description = description,
            defaultValue = defaultValue
        )
    }
}
