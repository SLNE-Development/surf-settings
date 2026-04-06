package dev.slne.surf.settings.core.common.rabbit.packet.request

import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.settings.core.common.rabbit.packet.response.ManyPlayerSettingsResponsePacket
import kotlinx.serialization.Serializable

@Serializable
data class LoadPlayerSettingsRequestPacket(
    val playerUuid: SerializableUUID
) : RabbitRequestPacket<ManyPlayerSettingsResponsePacket>()
