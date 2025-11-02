package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.*
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.*
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
                    packet.uid,
                    packet.identifier,
                    packet.displayName,
                    packet.description,
                    packet.category,
                    packet.defaultValue
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingDeletePacket(packet: ServerboundSettingDeletePacket) {
        packet.respond(ClientboundSettingDeleteResultPacket(settingRepository.delete(packet.uid)))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryAllPacket(packet: ServerboundSettingQueryAllPacket) {
        packet.respond(ClientboundSettingQueryManyPacket(settingRepository.all()))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryPacket(packet: ServerboundSettingQueryPacket) {
        packet.respond(ClientboundSettingQueryResultPacket(settingRepository.getSetting(packet.uid)))
    }

    @SurfNettyPacketHandler
    suspend fun handleSettingQueryByIdPacket(packet: ServerboundSettingQueryByIdPacket) {
        packet.respond(ClientboundSettingQueryResultPacket(settingRepository.getSetting(packet.identifier)))
    }

    @SurfNettyPacketHandler
    suspend fun handleCategoriesGetPacket(packet: ServerboundGetCategoriesPacket) {
        packet.respond(ClientboundCategoriesResultPacket(settingRepository.getCategories()))
    }
}