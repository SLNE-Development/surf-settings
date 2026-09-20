package dev.slne.surf.settings.core.client.command

import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.core.common.service.SettingsService
import net.kyori.adventure.text.Component
import java.util.*

object NiceToggle {
    val suggestions = listOf("enable", "disable", "on", "off", "an", "aus")

    fun parse(input: String): Boolean? = when (input) {
        "enable", "on", "an" -> true
        "disable", "off", "aus" -> false
        else -> null
    }
}

object SettingsCommandActions {
    fun toggle(playerUuid: UUID, setting: Setting): String {
        val playerSetting = getPlayerSetting(playerUuid, setting).copy()
        playerSetting.toggle()
        cacheAndSave(playerUuid, playerSetting)
        return playerSetting.getString()
    }

    fun set(playerUuid: UUID, setting: Setting, state: Boolean): String {
        val playerSetting = getPlayerSetting(playerUuid, setting).copy()
        playerSetting.settingValue = state.toString()
        cacheAndSave(playerUuid, playerSetting)
        return playerSetting.getString()
    }

    fun get(playerUuid: UUID, setting: Setting): String =
        getPlayerSetting(playerUuid, setting).getString()

    private fun getPlayerSetting(playerUuid: UUID, setting: Setting): PlayerSetting =
        SettingsService.getSettingForPlayerOrDefault(playerUuid, setting.name)
            ?: error("Setting not found")

    private fun cacheAndSave(playerUuid: UUID, playerSetting: PlayerSetting) {
        SettingsService.cachePlayerSetting(playerUuid, playerSetting)
        SettingsPlatform.launchAsync {
            SettingsService.savePlayerSetting(playerUuid, playerSetting)
        }
    }
}

object SettingsCommandMessages {
    val booleanOnly: Component
        get() = buildText {
            appendErrorPrefix()
            error("Nur Boolean Einstellungen können über den Befehl geändert werden.")
        }

    val invalidToggle: Component
        get() = buildText {
            appendErrorPrefix()
            error("Bitte gebe entweder 'enable', 'disable', 'on' oder 'off' an.")
        }

    val settingNotFound: Component
        get() = buildText {
            appendErrorPrefix()
            error("Die Einstellung wurde nicht gefunden.")
        }

    val noSettings: Component
        get() = buildText {
            appendErrorPrefix()
            error("Es sind keine Einstellungen verfügbar.")
        }

    fun changed(settingName: String, value: String): Component = buildText {
        appendSuccessPrefix()
        success("Die Einstellung ")
        variableValue(settingName)
        success(" wurde auf ")
        variableValue(value)
        success(" gesetzt.")
    }

    fun info(settingName: String, value: String): Component = buildText {
        appendInfoPrefix()
        info("Die Einstellung ")
        variableValue(settingName)
        info(" hat den Wert ")
        variableValue(value)
        info(".")
    }

    fun refreshed(settingCount: Int, elapsedMillis: Long): Component = buildText {
        appendSuccessPrefix()
        success("Es wurden alle Einstellungen neu geladen ($settingCount in ${elapsedMillis}ms)!")
    }

    fun list(settings: Collection<Setting>): Component = buildText {
        appendInfoPrefix()
        info("Verfügbare Einstellungen: ")
        variableValue(settings.joinToString { it.name })
    }
}
