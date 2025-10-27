package dev.slne.surf.settings.core.netty.protocol.clientbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.Setting
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_query_many", PacketFlow.CLIENTBOUND)
data class ClientboundSettingQueryManyPacket(
    val queries: Set<Setting>
) : ResponseNettyPacket()