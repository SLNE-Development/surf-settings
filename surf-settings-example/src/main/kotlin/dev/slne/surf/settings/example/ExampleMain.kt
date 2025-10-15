package dev.slne.surf.settings.example

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.settings.api.common.dsl.settings
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(ExampleMain::class.java)

class ExampleMain : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        surfSettingExampleCommand()

        logger.warning("The server is running the example surf settings plugin. This is only for demonstration purposes and should not be used in production!")

        settings {
            category {
                identifier = CATEGORY_ID
                displayName = "Beispiel Einstellungen"
                description = "Eine Kategorie mit Beispiel Einstellungen"
            }

            setting {
                identifier = ID_BOOLEAN
                withCategory(CATEGORY_ID)
                displayName = "Beispiel Boolean"
                description = "Eine Beispiel Boolean Einstellung"
                defaultValue = "true"
            }

            setting {
                identifier = ID_INT
                withCategory(CATEGORY_ID)
                displayName = "Beispiel Integer"
                description = "Eine Beispiel Integer Einstellung"
                defaultValue = "5"
            }

            setting {
                identifier = ID_TEXT
                withCategory(CATEGORY_ID)
                displayName = "Beispiel Text"
                description = "Eine Beispiel Text Einstellung"
                defaultValue = "Hallo Welt!"
            }
        }

        logger.info("Successfully registered example settings & category")
    }

    companion object {
        const val ID_BOOLEAN = "example-setting-boolean"
        const val ID_INT = "example-setting-int"
        const val ID_TEXT = "example-setting-text"
        const val CATEGORY_ID = "example"
    }
}