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
import dev.slne.surf.settings.server.repository.SettingCategoryRepository
import org.springframework.stereotype.Component

@Component
class SettingCategoryListener(
    private val settingCategoryRepository: SettingCategoryRepository
) {
    @SurfNettyPacketHandler
    suspend fun handleCreatePacket(packet: ServerboundSettingCategoryCreatePacket) {
        packet.respond(
            ClientboundSettingCategoryCreateResultPacket(
                settingCategoryRepository.createCategory(
                    packet.identifier, packet.displayName, packet.description
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleDeletePacket(packet: ServerboundSettingCategoryDeletePacket) {
        packet.respond(
            ClientboundSettingCategoryDeleteResultPacket(
                settingCategoryRepository.deleteCategory(
                    packet.category
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryPacket(packet: ServerboundSettingCategoryQueryPacket) {
        packet.respond(
            ClientboundSettingCategoryQueryResultPacket(
                settingCategoryRepository.getCategory(
                    packet.identifier
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllPacket(packet: ServerboundSettingCategoryQueryAllPacket) {
        packet.respond(ClientboundSettingCategoryQueryManyPacket(settingCategoryRepository.all()))
    }
}