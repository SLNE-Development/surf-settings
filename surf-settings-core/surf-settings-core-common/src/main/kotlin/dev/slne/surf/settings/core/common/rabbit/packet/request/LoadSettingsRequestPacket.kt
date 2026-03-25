package dev.slne.surf.settings.core.common.rabbit.packet.request

import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.settings.core.common.rabbit.packet.response.ManySettingsResponsePacket
import kotlinx.serialization.Serializable

@Serializable
object LoadSettingsRequestPacket : RabbitRequestPacket<ManySettingsResponsePacket>()