package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingDeleteResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_delete", PacketFlow.SERVERBOUND)
class ServerboundSettingDeletePacket(
    val identifier: String
) : RespondingNettyPacket<ClientboundSettingDeleteResultPacket>()