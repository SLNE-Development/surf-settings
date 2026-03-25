package dev.slne.surf.settings.core.common.rabbit.packet.response

import dev.slne.surf.rabbitmq.api.packet.RabbitResponsePacket
import dev.slne.surf.settings.api.setting.Setting
import kotlinx.serialization.Serializable

@Serializable
data class ManySettingsResponsePacket(
    val settings: List<Setting>
) : RabbitResponsePacket()
