package dev.slne.surf.settings.core.common.rabbit.packet.response

import dev.slne.surf.rabbitmq.api.packet.RabbitResponsePacket
import kotlinx.serialization.Serializable

@Serializable
data class ManyPlayerSettingsResponsePacket(
    val playerSettings: List<Pair<String, String>>
) : RabbitResponsePacket()
