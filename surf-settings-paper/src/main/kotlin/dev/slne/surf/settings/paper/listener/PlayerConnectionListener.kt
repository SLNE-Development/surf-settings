package dev.slne.surf.settings.paper.listener

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.settings.core.settingPlayerService
import dev.slne.surf.settings.paper.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerConnectionListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        plugin.launch {
            settingPlayerService.cachePlayer(
                settingPlayerService.getOrLoadOrCreatePlayerByUuid(
                    event.player.uniqueId,
                    event.player.name
                )
            )
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        settingPlayerService.invalidatePlayer(event.player.uniqueId)
    }
}