package dev.slne.surf.settings.minestom.command.argument

import dev.slne.minestom.lobby.api.command.commandapi.CommandAPI
import dev.slne.minestom.lobby.api.command.commandapi.CommandTree
import dev.slne.minestom.lobby.api.command.commandapi.argument.Argument
import dev.slne.minestom.lobby.api.command.commandapi.argument.CustomArgument
import dev.slne.minestom.lobby.api.command.commandapi.argument.StringArgument
import dev.slne.minestom.lobby.api.command.commandapi.suggestion.ArgumentSuggestions
import dev.slne.surf.settings.core.client.command.NiceToggle
import dev.slne.surf.settings.core.client.command.SettingsCommandMessages

class NiceToggleArgument(nodeName: String) :
    CustomArgument<Boolean, String>(StringArgument(nodeName), { info ->
        NiceToggle.parse(info.currentInput)
            ?: CommandAPI.failWithMessage(SettingsCommandMessages.invalidToggle)
    }) {
    init {
        replaceSuggestions(
            ArgumentSuggestions.strings(*NiceToggle.suggestions.toTypedArray())
        )
    }
}

inline fun CommandTree.niceToggleArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<Boolean>.() -> Unit = {}
): CommandTree = then(
    NiceToggleArgument(nodeName).setOptional(optional = optional).apply(block)
)
