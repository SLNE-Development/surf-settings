package dev.slne.surf.settings.example

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.settings.api.common.surfSettingApi
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(ExampleMain::class.java)

class ExampleMain : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        val boolResult = surfSettingApi.createSettingIfNotExists(
            "example-setting-boolean",
            "Examples",
            "Example Boolean Setting",
            "This is an example boolean setting",
            "true"
        )

        val intResult = surfSettingApi.createSettingIfNotExists(
            "example-setting-int",
            "Examples",
            "Example Int Setting",
            "This is an example int setting",
            "2"
        )

        println("Bool result: $boolResult")
        println("Int result: $intResult")
    }

    companion object {
        const val ID_BOOLEAN = "example-setting-boolean"
        const val ID_INT = "example-setting-int"
        const val ID_TEXT = "example-setting-text"
        const val CATEGORY = "Examples"
    }
}