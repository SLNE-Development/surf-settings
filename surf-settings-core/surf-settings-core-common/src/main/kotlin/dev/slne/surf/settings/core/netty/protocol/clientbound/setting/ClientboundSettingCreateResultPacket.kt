package dev.slne.surf.settings.core.netty.protocol.clientbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.result.setting.SettingCreateResult
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_create_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingCreateResultPacket(
    val result: SettingCreateResult
) : ResponseNettyPacket()