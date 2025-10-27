package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingCreateResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingDeleteResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingQueryManyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingQueryResultPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingCreatePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingDeletePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingQueryAllPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingQueryPacket
import dev.slne.surf.settings.server.repository.SettingRepository
import org.springframework.stereotype.Component

@Component
class SettingPacketListener(
    private val settingRepository: SettingRepository
) {
    @SurfNettyPacketHandler
    suspend fun handleSettingCreatePacket(packet: ServerboundSettingCreatePacket) {
        packet.respond(
            ClientboundSettingCreateResultPacket(
                settingRepository.createSetting(
                    packet.identifier,
                    packet.category,
                    packet.displayName,
                    packet.description,
                    packet.defaultValue
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingDeletePacket(packet: ServerboundSettingDeletePacket) {
        packet.respond(ClientboundSettingDeleteResultPacket(settingRepository.delete(packet.identifier)))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryAllPacket(packet: ServerboundSettingQueryAllPacket) {
        packet.respond(ClientboundSettingQueryManyPacket(settingRepository.all()))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryPacket(packet: ServerboundSettingQueryPacket) {
        packet.respond(ClientboundSettingQueryResultPacket(settingRepository.getSetting(packet.identifier)))
    }
}