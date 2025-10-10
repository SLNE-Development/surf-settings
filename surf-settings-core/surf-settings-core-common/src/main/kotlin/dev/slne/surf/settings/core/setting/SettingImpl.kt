package dev.slne.surf.settings.core.setting

import dev.slne.surf.settings.api.common.setting.Setting
import kotlinx.serialization.Serializable

@Serializable
class SettingImpl(
    override val id: Long,
    override val identifier: String,
    override val category: String,
    override val displayName: String,
    override val description: String,
    override val defaultValue: String
) : Setting