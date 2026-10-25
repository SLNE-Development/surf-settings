package dev.slne.surf.settings.minestom.menu

import dev.slne.surf.settings.api.setting.SettingKeys
import net.minestom.server.item.Material

val settingKeyMappings = mapOf(
    SettingKeys.CONNECTION_MESSAGES to SettingKeyDisplay(
        "Verbindungsnachrichten",
        "Zeigt Nachrichten an, wenn Spieler den Server betreten oder verlassen",
        Material.ENDER_PEARL,
        SettingCategory.CHAT
    ),
    SettingKeys.CHAT_DEATH_MESSAGES to SettingKeyDisplay(
        "Todesnachrichten",
        "Zeigt Todesnachrichten im Chat an",
        Material.RED_BED,
        SettingCategory.CHAT
    ),
    SettingKeys.DIRECT_MESSAGES to SettingKeyDisplay(
        "Private Nachrichten",
        "Benachrichtigungen für private Nachrichten",
        Material.PAPER,
        SettingCategory.CHAT
    ),
    SettingKeys.CHAT_PINGS to SettingKeyDisplay(
        "Chat Pings",
        "Benachrichtigung, sobald dich jemand erwähnt",
        Material.BELL,
        SettingCategory.CHAT
    ),
    SettingKeys.CLAN_CHAT_MESSAGES to SettingKeyDisplay(
        "Clan-Chat",
        "Zeigt Nachrichten aus dem Clan-Chat an",
        Material.WRITABLE_BOOK,
        SettingCategory.CLAN
    ),
    SettingKeys.CLAN_INVITES to SettingKeyDisplay(
        "Clan-Einladungen",
        "Benachrichtigungen für Einladungen zu Clans",
        Material.SHIELD,
        SettingCategory.CLAN
    ),
    SettingKeys.FRIEND_NOTIFICATIONS to SettingKeyDisplay(
        "Freunde-Benachrichtigungen",
        "Benachrichtigungen über Aktivitäten deiner Freunde",
        Material.HEART_OF_THE_SEA,
        SettingCategory.FRIENDS
    ),
    SettingKeys.FRIEND_REQUEST_NOTIFICATIONS to SettingKeyDisplay(
        "Freundschaftsanfragen",
        "Benachrichtigungen für neue Freundschaftsanfragen",
        Material.PLAYER_HEAD,
        SettingCategory.FRIENDS
    ),
    SettingKeys.FRIEND_SOUNDS to SettingKeyDisplay(
        "Freunde-Sounds",
        "Spielt Sounds bei Benachrichtigungen von Freunden ab",
        Material.AMETHYST_SHARD,
        SettingCategory.FRIENDS
    ),
    SettingKeys.LOBBY_SCROLL_SOUND to SettingKeyDisplay(
        "Scroll-Sound",
        "Spielt einen Sound beim Scrollen in der Lobby ab",
        Material.NOTE_BLOCK,
        SettingCategory.LOBBY
    ),
    SettingKeys.SHOW_NAMETAGS to SettingKeyDisplay(
        "Nametags anzeigen",
        "Zeigt die Nametags anderer Spieler an",
        Material.NAME_TAG,
        SettingCategory.GENERAL
    ),
    SettingKeys.AUTO_QUEUE_AFTER_RECONNECT to SettingKeyDisplay(
        "Automatische Rückkehr",
        "Bringt dich nach einem kurzen Verbindungsabbruch automatisch zurück auf deinen vorherigen Server",
        Material.RECOVERY_COMPASS,
        SettingCategory.GENERAL
    ),
    SettingKeys.SHOW_SCOREBOARD to SettingKeyDisplay(
        "Scoreboard anzeigen",
        "Zeigt das Scoreboard auf der rechten Seite an",
        Material.SLIME_BLOCK,
        SettingCategory.GENERAL
    ),
).mapKeys { it.key.name }