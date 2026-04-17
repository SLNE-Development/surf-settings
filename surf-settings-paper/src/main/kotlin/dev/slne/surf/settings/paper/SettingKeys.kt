package dev.slne.surf.settings.paper

import dev.slne.surf.settings.api.setting.SettingKey
import org.bukkit.NamespacedKey

/**
 * Central registry of all setting keys used by the plugin.
 */
object SettingKeys {
    // Chat
    val CHAT_PINGS = SettingKey.ofBoolean(NamespacedKey("chat", "pings"), defaultValue = true)
    val CHAT_DEATH_MESSAGES =
        SettingKey.ofBoolean(NamespacedKey("chat", "death-messages"), defaultValue = true)
    val DIRECT_MESSAGES =
        SettingKey.ofBoolean(NamespacedKey("chat", "direct-messages"), defaultValue = true)

    // Lobby
    val LOBBY_SCROLL_SOUND =
        SettingKey.ofBoolean(NamespacedKey("lobby", "scroll-sound"), defaultValue = false)

    // Clan
    val CLAN_INVITES = SettingKey.ofBoolean(NamespacedKey("clan", "invites"), defaultValue = true)
    val CLAN_CHAT_MESSAGES =
        SettingKey.ofBoolean(NamespacedKey("clan", "chat-messages"), defaultValue = true)

    // Friends
    val FRIEND_REQUEST_NOTIFICATIONS = SettingKey.ofBoolean(
        NamespacedKey("friend", "request-notifications"),
        defaultValue = true
    )
    val FRIEND_NOTIFICATIONS =
        SettingKey.ofBoolean(
            NamespacedKey("friend", "notifications"),
            defaultValue = true
        )
    val FRIEND_SOUNDS =
        SettingKey.ofBoolean(NamespacedKey("friend", "sounds"), defaultValue = true)
}

