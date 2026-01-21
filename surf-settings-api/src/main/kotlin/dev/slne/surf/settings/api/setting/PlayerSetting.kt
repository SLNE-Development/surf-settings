package dev.slne.surf.settings.api.setting

data class PlayerSetting(
    val setting: Setting,
    val settingValue: String
) {
    fun getBoolean(): Boolean = settingValue.toBoolean()
    fun getInt(): Int = settingValue.toInt()
    fun getDouble(): Double = settingValue.toDouble()
    fun getString(): String = settingValue
}
