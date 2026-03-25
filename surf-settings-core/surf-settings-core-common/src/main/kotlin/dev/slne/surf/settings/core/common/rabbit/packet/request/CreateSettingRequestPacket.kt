package dev.slne.surf.settings.core.common.rabbit.packet.request

import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreateSettingRequestPacket(
    val name: String,
    val defaultValue: String
) :
    RabbitRequestPacket<PrimitiveResponse.BooleanResponsePacket>()
