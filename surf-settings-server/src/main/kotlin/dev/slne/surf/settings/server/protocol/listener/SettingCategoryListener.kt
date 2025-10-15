package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryCreateResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryDeleteResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryQueryManyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryQueryResultPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryCreatePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryDeletePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryAllPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryPacket
import dev.slne.surf.settings.server.service.SettingCategoryService
import org.springframework.stereotype.Component

@Component
class SettingCategoryListener(
    private val settingCategoryService: SettingCategoryService
) {
    @SurfNettyPacketHandler
    suspend fun handleCreatePacket(packet: ServerboundSettingCategoryCreatePacket) {
        packet.respond(
            ClientboundSettingCategoryCreateResultPacket(
                settingCategoryService.create(
                    packet.category
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleDeletePacket(packet: ServerboundSettingCategoryDeletePacket) {
        packet.respond(
            ClientboundSettingCategoryDeleteResultPacket(
                settingCategoryService.delete(
                    packet.category
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryPacket(packet: ServerboundSettingCategoryQueryPacket) {
        packet.respond(
            ClientboundSettingCategoryQueryResultPacket(
                settingCategoryService.query(
                    packet.identifier
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllPacket(packet: ServerboundSettingCategoryQueryAllPacket) {
        packet.respond(ClientboundSettingCategoryQueryManyPacket(settingCategoryService.all()))
    }
}