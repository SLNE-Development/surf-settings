package dev.slne.surf.settings.core.common.rabbit.packet.request

import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.surfapi.core.api.serializer.java.uuid.SerializableUUID
import kotlinx.serialization.Serializable

@Serializable
data class SavePlayerSettingRequestPacket(
    val playerUuid: SerializableUUID,
    val playerSetting: PlayerSetting
) : RabbitRequestPacket<PrimitiveResponse.BooleanResponsePacket>()
