package dev.slne.surf.settings.server.protocol.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryModifyResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryManyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryResultPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryResetResultPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.entry.*
import dev.slne.surf.settings.server.service.SettingEntryService
import org.springframework.stereotype.Component

@Component
class SettingEntryPacketListener(
    private val settingEntryService: SettingEntryService
) {
    @SurfNettyPacketHandler
    suspend fun handleModifyPacket(packet: ServerboundSettingEntryModifyPacket) {
        packet.respond(
            ClientboundSettingEntryModifyResultPacket(
                settingEntryService.modify(
                    packet.player,
                    packet.setting
                )
            )
        )
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllByPlayerPacket(packet: ServerboundSettingEntryQueryAllByPlayerPacket) {
        packet.respond(ClientboundSettingEntryQueryManyPacket(settingEntryService.all(packet.playerUuid)))
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryAllPacket(packet: ServerboundSettingEntryQueryAllPacket) {
        packet.respond(ClientboundSettingEntryQueryManyPacket(settingEntryService.all()))
    }

    @SurfNettyPacketHandler
    suspend fun handleQueryPacket(packet: ServerboundSettingEntryQueryPacket) {
        packet.respond(
            ClientboundSettingEntryQueryResultPacket(
                settingEntryService.query(
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
                settingEntryService.reset(
                    packet.player,
                    packet.setting
                )
            )
        )
    }
}