package dev.slne.surf.settings.core.netty.protocol.serverbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.ClientboundSettingQueryResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_query", PacketFlow.SERVERBOUND)
class ServerboundSettingQueryPacket(
    val identifier: String
) : RespondingNettyPacket<ClientboundSettingQueryResultPacket>()