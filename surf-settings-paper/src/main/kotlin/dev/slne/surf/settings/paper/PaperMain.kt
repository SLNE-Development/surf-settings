package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import org.bukkit.plugin.java.JavaPlugin

class PaperMain : SuspendingJavaPlugin() {
    
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)