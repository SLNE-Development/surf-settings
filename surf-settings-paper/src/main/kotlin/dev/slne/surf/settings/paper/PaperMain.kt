package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.settings.paper.command.settingsCommand
import org.bukkit.plugin.java.JavaPlugin

class PaperMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        settingsCommand()
    }
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)