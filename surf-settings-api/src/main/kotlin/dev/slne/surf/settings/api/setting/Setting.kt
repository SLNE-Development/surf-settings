package dev.slne.surf.settings.api.setting

data class Setting(
    val name: String,
    val defaultValue: String
) {
    fun isBoolean() = defaultValue.equals("true", ignoreCase = true) || defaultValue.equals(
        "false",
        ignoreCase = true
    )

    fun isInt() = defaultValue.toIntOrNull() != null
    fun isDouble() = defaultValue.toDoubleOrNull() != null
    fun isString() = !isBoolean() && !isInt() && !isDouble()
}
