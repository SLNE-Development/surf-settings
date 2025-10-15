package dev.slne.surf.settings.example

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.settings.api.common.surfSettingApi

fun toggleExampleBooleanSettingCommand() = commandTree("toggleexamplebooleansetting") {
    playerExecutor { player, args ->
        plugin.launch {
            val setting = surfSettingApi.querySetting("example-setting-boolean")
        }
    }
}