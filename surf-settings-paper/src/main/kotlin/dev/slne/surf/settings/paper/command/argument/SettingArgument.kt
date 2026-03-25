package dev.slne.surf.settings.paper.command.argument

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandTree
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.service.settingsService
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText

class SettingArgument(nodeName: String) :
    CustomArgument<Setting, String>(StringArgument(nodeName), { info ->
        settingsService.getSettingByName(info.input)
            ?: throw CustomArgumentException.fromAdventureComponent(
                buildText {
                    appendErrorPrefix()
                    error("Die Einstellung wurde nicht gefunden.")
                })
    }) {
    init {
        this.replaceSuggestions(
            ArgumentSuggestions.stringCollection {
                settingsService.settings.map { it.name }
            }
        )
    }
}

inline fun CommandTree.settingArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandTree = then(
    SettingArgument(nodeName).setOptional(optional).apply(block)
)

inline fun Argument<*>.settingArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): Argument<*> = then(
    SettingArgument(nodeName).setOptional(optional).apply(block)
)

inline fun CommandAPICommand.settingArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandAPICommand =
    withArguments(SettingArgument(nodeName).setOptional(optional).apply(block))