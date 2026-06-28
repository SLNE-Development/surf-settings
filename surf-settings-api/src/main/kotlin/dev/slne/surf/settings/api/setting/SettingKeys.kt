package dev.slne.surf.settings.api.setting

import dev.slne.surf.api.core.messages.adventure.key

/**
 * Central registry of all setting keys used by the plugin.
 */
object SettingKeys {
    // Chat
    val CHAT_PINGS = SettingKey.ofBoolean(key("chat", "pings"), defaultValue = true)
    val CHAT_DEATH_MESSAGES =
        SettingKey.ofBoolean(key("chat", "death-messages"), defaultValue = true)
    val DIRECT_MESSAGES =
        SettingKey.ofBoolean(key("chat", "direct-messages"), defaultValue = true)
    val CONNECTION_MESSAGES = SettingKey.ofBoolean(
        key("chat", "connection-messages"),
        defaultValue = true
    )

    // Lobby
    val LOBBY_SCROLL_SOUND =
        SettingKey.ofBoolean(key("lobby", "scroll-sound"), defaultValue = false)

    // Clan
    val CLAN_INVITES = SettingKey.ofBoolean(key("clan", "invites"), defaultValue = true)
    val CLAN_CHAT_MESSAGES =
        SettingKey.ofBoolean(key("clan", "chat-messages"), defaultValue = true)

    // Friends
    val FRIEND_REQUEST_NOTIFICATIONS = SettingKey.ofBoolean(
        key("friend", "request-notifications"),
        defaultValue = true
    )
    val FRIEND_NOTIFICATIONS =
        SettingKey.ofBoolean(
            key("friend", "notifications"),
            defaultValue = true
        )
    val FRIEND_SOUNDS =
        SettingKey.ofBoolean(key("friend", "sounds"), defaultValue = true)

    // Nametag

    val SHOW_NAMETAGS = SettingKey.ofBoolean(key("nametag", "show-nametags"), defaultValue = true)
}