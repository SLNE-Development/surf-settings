package dev.slne.surf.settings.paper.menu

import dev.slne.surf.settings.api.setting.SettingKeys
import org.bukkit.Material

val settingKeyMappings = mapOf(
    SettingKeys.CHAT_PINGS to SettingKeyDisplay(
        "Chat Pings",
        "Benachrichtigung, sobald dich jemand erwähnt",
        Material.BELL
    ),
    SettingKeys.CHAT_DEATH_MESSAGES to SettingKeyDisplay(
        "Todesnachrichten",
        "Zeigt Todesnachrichten im Chat an",
        Material.RED_BED
    ),
    SettingKeys.DIRECT_MESSAGES to SettingKeyDisplay(
        "Private Nachrichten",
        "Benachrichtigungen für private Nachrichten",
        Material.PAPER
    ),
    SettingKeys.CONNECTION_MESSAGES to SettingKeyDisplay(
        "Verbindungsnachrichten",
        "Zeigt Nachrichten an, wenn Spieler den Server betreten oder verlassen",
        Material.ENDER_PEARL
    ),
    SettingKeys.LOBBY_SCROLL_SOUND to SettingKeyDisplay(
        "Scroll-Sound",
        "Spielt einen Sound beim Scrollen im Lobby-Menü ab",
        Material.NOTE_BLOCK
    ),
    SettingKeys.CLAN_INVITES to SettingKeyDisplay(
        "Clan-Einladungen",
        "Benachrichtigungen für Einladungen zu Clans",
        Material.SHIELD
    ),
    SettingKeys.CLAN_CHAT_MESSAGES to SettingKeyDisplay(
        "Clan-Chat",
        "Zeigt Nachrichten aus dem Clan-Chat an",
        Material.WRITABLE_BOOK
    ),
    SettingKeys.FRIEND_REQUEST_NOTIFICATIONS to SettingKeyDisplay(
        "Freundschaftsanfragen",
        "Benachrichtigungen für neue Freundschaftsanfragen",
        Material.PLAYER_HEAD
    ),
    SettingKeys.FRIEND_NOTIFICATIONS to SettingKeyDisplay(
        "Freunde-Benachrichtigungen",
        "Benachrichtigungen über Aktivitäten deiner Freunde",
        Material.HEART_OF_THE_SEA
    ),
    SettingKeys.FRIEND_SOUNDS to SettingKeyDisplay(
        "Freunde-Sounds",
        "Spielt Sounds bei Benachrichtigungen von Freunden ab",
        Material.AMETHYST_SHARD
    ),
    SettingKeys.SHOW_NAMETAGS to SettingKeyDisplay(
        "Nametags anzeigen",
        "Zeigt die Nametags anderer Spieler an",
        Material.NAME_TAG
    ),
    SettingKeys.SHOW_SCOREBOARD to SettingKeyDisplay(
        "Scoreboard anzeigen",
        "Zeigt das Scoreboard auf der rechten Seite an",
        Material.SLIME_BLOCK
    )
).mapKeys { it.key.name }