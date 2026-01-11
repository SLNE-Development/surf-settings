package dev.slne.surf.settings.backend.table

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object SettingPlayerTable : LongIdTable("settings_players") {
    val playerUuid = uuid("player_uuid").uniqueIndex()
    val playerName = varchar("player_name", 255)
}