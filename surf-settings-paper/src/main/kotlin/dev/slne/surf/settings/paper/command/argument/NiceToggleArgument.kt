package dev.slne.surf.settings.paper.command.argument

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandTree
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.slne.surf.settings.core.client.command.NiceToggle
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages

class NiceToggleArgument(nodeName: String) :
    CustomArgument<Boolean, String>(StringArgument(nodeName), { info ->
        NiceToggle.parse(info.input())
            ?: throw CustomArgumentException.fromAdventureComponent(
                SettingsCommandMessages.invalidToggle
            )
    }) {
    init {
        this.replaceSuggestions(
            ArgumentSuggestions.strings(*NiceToggle.suggestions.toTypedArray())
        )
    }
}

inline fun CommandAPICommand.niceToggleArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandAPICommand =
    withArguments(NiceToggleArgument(nodeName).setOptional(optional).apply(block))

inline fun CommandTree.niceToggleArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandTree = then(
    NiceToggleArgument(nodeName).setOptional(optional).apply(block)
)

inline fun Argument<*>.niceToggleArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): Argument<*> = then(
    NiceToggleArgument(nodeName).setOptional(optional).apply(block)
)
