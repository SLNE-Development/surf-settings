package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingQueryResultPacket
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SurfNettyPacket("setting:serverbound:setting_query", PacketFlow.SERVERBOUND)
class ServerboundSettingQueryPacket(
    val uid: @Contextual UUID
) : RespondingNettyPacket<ClientboundSettingQueryResultPacket>()