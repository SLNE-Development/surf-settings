package dev.slne.surf.settings.velocity.listener

import com.google.common.eventbus.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import dev.slne.surf.api.core.util.logger
import dev.slne.surf.settings.core.common.service.SettingsService

object PlayerConnectionListener {
    private val log = logger()

    @Subscribe
    suspend fun onPostLogin(event: PostLoginEvent) {
        val uuid = event.player.uniqueId

        try {
            SettingsService.cachePlayerSettings(uuid)
        } catch (e: Exception) {
            log.atWarning()
                .withCause(e)
                .log("Failed to cache settings for %s", uuid)
        }
    }

    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        SettingsService.invalidatePlayerSettingsCache(event.player.uniqueId)
    }
}