package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryModifyResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryManyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryResetResultPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.entry.*
import dev.slne.surf.settings.server.repository.SettingEntryRepository
import org.springframework.stereotype.Component

@Component
class SettingEntryPacketListener(
    private val settingEntryRepository: SettingEntryRepository
) {
    @SurfNettyPacketHandler
    suspend fun handleModifyPacket(packet: ServerboundSettingEntryModifyPacket) {
        packet.respond(
            ClientboundSettingEntryModifyResultPacket(
                settingEntryRepository.modify(
                    packet.player,
                    packet.setting,
                    packet.value
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllByPlayerPacket(packet: ServerboundSettingEntryQueryAllByPlayerPacket) {
        packet.respond(ClientboundSettingEntryQueryManyPacket(settingEntryRepository.getAll(packet.playerUuid)))
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllPacket(packet: ServerboundSettingEntryQueryAllPacket) {
        packet.respond(ClientboundSettingEntryQueryManyPacket(settingEntryRepository.getAll()))
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryPacket(packet: ServerboundSettingEntryQueryPacket) {
        packet.respond(
            ClientboundSettingEntryQueryResultPacket(
                settingEntryRepository.getEntry(
                    packet.player,
                    packet.setting
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleResetPacket(packet: ServerboundSettingEntryResetPacket) {
        packet.respond(
            ClientboundSettingEntryResetResultPacket(
                settingEntryRepository.reset(
                    packet.player,
                    packet.setting
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryByCategoryPacket(packet: ServerboundSettingEntryQueryByCategoryPacket) {
        packet.respond(
            ClientboundSettingEntryQueryManyPacket(
                settingEntryRepository.getAllByCategoryPlayer(packet.player, packet.category)
            )
        )
    }
}