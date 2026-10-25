package dev.slne.surf.settings.paper.menu

import org.bukkit.Material

enum class SettingCategory(
    val displayName: String,
    val description: String,
    val material: Material,
) {
    CHAT("Chat", "Nachrichten und Benachrichtigungen im Chat", Material.OAK_SIGN),
    CLAN("Clan", "Clan-Chat und Clan-Einladungen", Material.WHITE_BANNER),
    FRIENDS("Freunde", "Benachrichtigungen und Sounds deiner Freunde", Material.POPPY),
    LOBBY("Lobby", "Einstellungen für die Lobby", Material.COMPASS),
    GENERAL("Allgemeines", "Anzeige- und Verbindungseinstellungen", Material.COMPARATOR),
}
