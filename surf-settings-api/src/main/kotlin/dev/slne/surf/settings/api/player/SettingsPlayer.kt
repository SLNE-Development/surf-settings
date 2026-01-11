package dev.slne.surf.settings.api.player

import dev.slne.surf.settings.api.setting.PlayerSetting
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.*

data class SettingsPlayer(
    val name: String,
    val uuid: UUID,
    val settings: ObjectSet<PlayerSetting>
)
