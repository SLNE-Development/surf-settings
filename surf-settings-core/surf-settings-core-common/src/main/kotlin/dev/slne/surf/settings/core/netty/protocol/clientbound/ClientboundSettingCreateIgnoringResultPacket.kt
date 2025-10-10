package dev.slne.surf.settings.core.netty.protocol.clientbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.result.SettingCreateIgnoringResult
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_create_ignoring_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingCreateIgnoringResultPacket(
    val result: SettingCreateIgnoringResult
) : ResponseNettyPacket()