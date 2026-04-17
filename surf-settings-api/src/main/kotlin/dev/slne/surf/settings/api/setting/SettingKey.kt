package dev.slne.surf.settings.api.setting

import net.kyori.adventure.key.Key

/**
 * A typesafe setting key that associates a namespaced key with a specific value type.
 *
 * @param T the type of the setting value
 * @param key the namespaced key identifying this setting
 * @param defaultValue the default value for this setting
 * @param serializer converts a value of type [T] to a [String]
 * @param deserializer converts a [String] back to a value of type [T]
 */
class SettingKey<T : Any>(
    val key: Key,
    val defaultValue: T,
    val serializer: (T) -> String,
    val deserializer: (String) -> T
) {
    /**
     * The string representation of the key, used for storage.
     */
    val name: String get() = key.asString()

    fun serialize(value: T): String = serializer(value)
    fun deserialize(value: String): T = deserializer(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SettingKey<*>) return false
        return key == other.key
    }

    override fun hashCode(): Int = key.hashCode()
    override fun toString(): String = "SettingKey($key)"

    companion object {
        /**
         * Creates a [SettingKey] for [Boolean] values.
         */
        fun ofBoolean(key: Key, defaultValue: Boolean = false): SettingKey<Boolean> =
            SettingKey(key, defaultValue, { it.toString() }, { it.toBoolean() })

        /**
         * Creates a [SettingKey] for [Int] values.
         */
        fun ofInt(key: Key, defaultValue: Int = 0): SettingKey<Int> =
            SettingKey(key, defaultValue, { it.toString() }, { it.toInt() })

        /**
         * Creates a [SettingKey] for [Double] values.
         */
        fun ofDouble(key: Key, defaultValue: Double = 0.0): SettingKey<Double> =
            SettingKey(key, defaultValue, { it.toString() }, { it.toDouble() })

        /**
         * Creates a [SettingKey] for [String] values.
         */
        fun ofString(key: Key, defaultValue: String = ""): SettingKey<String> =
            SettingKey(key, defaultValue, { it }, { it })
    }
}

