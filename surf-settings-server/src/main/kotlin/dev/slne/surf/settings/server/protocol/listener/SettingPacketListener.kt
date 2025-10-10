package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.*
import dev.slne.surf.settings.core.netty.protocol.serverbound.*
import dev.slne.surf.settings.server.service.SettingService
import org.springframework.stereotype.Component

@Component
class SettingPacketListener(
    private val settingService: SettingService
) {
    @SurfNettyPacketHandler
    suspend fun handleSettingCreatePacketIgnoring(packet: ServerboundSettingCreateIgnoringPacket) {
        packet.respond(
            ClientboundSettingCreateIgnoringResultPacket(
                settingService.createIfNotExists(
                    packet.setting.setting
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingCreatePacket(packet: ServerboundSettingCreatePacket) {
        packet.respond(ClientboundSettingCreateResultPacket(settingService.createSetting(packet.setting.setting)))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingDeletePacket(packet: ServerboundSettingDeletePacket) {
        packet.respond(ClientboundSettingDeleteResultPacket(settingService.delete(packet.identifier)))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryAllPacket(packet: ServerboundSettingQueryAllPacket) {
        packet.respond(ClientboundSettingQueryManyPacket(settingService.queryAll()))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryPacket(packet: ServerboundSettingQueryPacket) {
        packet.respond(ClientboundSettingQueryResultPacket(settingService.query(packet.identifier)))
    }
}