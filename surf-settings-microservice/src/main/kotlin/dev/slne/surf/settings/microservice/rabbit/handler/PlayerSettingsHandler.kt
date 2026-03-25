package dev.slne.surf.settings.microservice.rabbit.handler

import dev.slne.surf.rabbitmq.api.handler.RabbitHandler
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import dev.slne.surf.settings.core.common.rabbit.packet.request.LoadPlayerSettingsRequestPacket
import dev.slne.surf.settings.core.common.rabbit.packet.request.SavePlayerSettingRequestPacket
import dev.slne.surf.settings.core.common.rabbit.packet.response.ManyPlayerSettingsResponsePacket
import dev.slne.surf.settings.microservice.repository.playerSettingsRepository
import kotlinx.coroutines.launch

object PlayerSettingsHandler {
    @RabbitHandler
    fun handleLoadPlayerSettingsRequestPacket(packet: LoadPlayerSettingsRequestPacket) =
        packet.launch {
            packet.respond(
                ManyPlayerSettingsResponsePacket(
                    playerSettingsRepository.loadSettingsByPlayerUuid(packet.playerUuid)
                )
            )
        }

    @RabbitHandler
    fun handleSavePlayerSettingRequestPacket(packet: SavePlayerSettingRequestPacket) =
        packet.launch {
            playerSettingsRepository.savePlayerSetting(
                packet.playerUuid,
                packet.playerSetting
            )
            packet.respond(
                PrimitiveResponse.BooleanResponsePacket(
                    true
                )
            )
        }
}