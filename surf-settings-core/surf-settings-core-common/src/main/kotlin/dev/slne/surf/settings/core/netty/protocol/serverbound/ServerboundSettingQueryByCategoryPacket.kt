package dev.slne.surf.settings.core.netty.protocol.serverbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.ClientboundSettingQueryManyPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_query_category", PacketFlow.SERVERBOUND)
class ServerboundSettingQueryByCategoryPacket(
    val category: String
) : RespondingNettyPacket<ClientboundSettingQueryManyPacket>()