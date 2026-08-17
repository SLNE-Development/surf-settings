package dev.slne.surf.settings.core.client

import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.SettingKeys

suspend fun createDefaultSettings() {
    SurfSettingsApi.createSetting(SettingKeys.CHAT_PINGS)
    SurfSettingsApi.createSetting(SettingKeys.CHAT_DEATH_MESSAGES)
    SurfSettingsApi.createSetting(SettingKeys.DIRECT_MESSAGES)
    SurfSettingsApi.createSetting(SettingKeys.CONNECTION_MESSAGES)
    SurfSettingsApi.createSetting(SettingKeys.LOBBY_SCROLL_SOUND)
    SurfSettingsApi.createSetting(SettingKeys.CLAN_INVITES)
    SurfSettingsApi.createSetting(SettingKeys.CLAN_CHAT_MESSAGES)
    SurfSettingsApi.createSetting(SettingKeys.FRIEND_REQUEST_NOTIFICATIONS)
    SurfSettingsApi.createSetting(SettingKeys.FRIEND_NOTIFICATIONS)
    SurfSettingsApi.createSetting(SettingKeys.FRIEND_SOUNDS)
    SurfSettingsApi.createSetting(SettingKeys.SHOW_NAMETAGS)
    SurfSettingsApi.createSetting(SettingKeys.SHOW_SCOREBOARD)
}
