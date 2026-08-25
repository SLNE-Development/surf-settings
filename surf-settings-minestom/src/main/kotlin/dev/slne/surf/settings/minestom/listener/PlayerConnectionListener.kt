package dev.slne.surf.settings.minestom.listener

import dev.slne.minestom.lobby.api.event.EventRegistrar
import dev.slne.minestom.lobby.api.extension.addListener
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.core.common.service.SettingsService
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerSpawnEvent

class PlayerConnectionListener : EventRegistrar {
    override fun register(node: EventNode<Event>) {
        node.addListener<PlayerSpawnEvent> { event ->
            if (event.isFirstSpawn) {
                val player = event.player
                SettingsPlatform.launchAsync {
                    SettingsService.cachePlayerSettings(player.uuid)

                    if (!player.isOnline) {
                        SettingsService.invalidatePlayerSettingsCache(player.uuid)
                    }
                }
            }
        }

        node.addListener<PlayerDisconnectEvent> { event ->
            SettingsService.invalidatePlayerSettingsCache(event.player.uuid)
        }
    }
}
