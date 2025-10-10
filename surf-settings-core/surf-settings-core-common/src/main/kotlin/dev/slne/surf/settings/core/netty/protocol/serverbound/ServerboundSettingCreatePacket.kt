package dev.slne.surf.settings.core.netty.protocol.serverbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.api.common.setting.SettingEntry
import dev.slne.surf.settings.core.netty.protocol.clientbound.ClientboundSettingCreateResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_create", PacketFlow.SERVERBOUND)
class ServerboundSettingCreatePacket(
    val setting: SettingEntry
) : RespondingNettyPacket<ClientboundSettingCreateResultPacket>()