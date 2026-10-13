package dev.slne.surf.settings.minestom.command.argument

import dev.slne.surf.api.minestom.command.CommandAPI
import dev.slne.surf.api.minestom.command.CommandTree
import dev.slne.surf.api.minestom.command.argument.Argument
import dev.slne.surf.api.minestom.command.argument.CustomArgument
import dev.slne.surf.api.minestom.command.argument.StringArgument
import dev.slne.surf.api.minestom.command.suggestion.ArgumentSuggestions
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages
import dev.slne.surf.settings.core.common.service.SettingsService

class SettingArgument(nodeName: String) :
    CustomArgument<Setting, String>(StringArgument(nodeName), { info ->
        SettingsService.getSettingByName(info.currentInput)
            ?: CommandAPI.failWithMessage(SettingsCommandMessages.settingNotFound)
    }) {
    init {
        replaceSuggestions(
            ArgumentSuggestions.stringCollection {
                SettingsService.settings.map { it.name }
            }
        )
    }
}

inline fun CommandTree.settingArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<Setting>.() -> Unit = {}
): CommandTree = then(
    SettingArgument(nodeName).setOptional(optional = optional).apply(block)
)
