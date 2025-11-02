package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingQueryManyPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_query_all", PacketFlow.SERVERBOUND)
class ServerboundSettingQueryAllPacket :
    RespondingNettyPacket<ClientboundSettingQueryManyPacket>()