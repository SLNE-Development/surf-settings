package dev.slne.surf.settings.core.netty.protocol.clientbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_delete_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingDeleteResultPacket(
    val result: Boolean
) : ResponseNettyPacket()