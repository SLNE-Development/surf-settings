package dev.slne.surf.settings.core.netty.protocol.serverbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.cloud.api.common.player.CloudPlayer
import dev.slne.surf.settings.api.common.setting.ConfiguredSetting
import dev.slne.surf.settings.core.netty.protocol.clientbound.ClientboundSettingModifyResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_modify", PacketFlow.SERVERBOUND)
class ServerboundSettingModifyPacket(
    val player: CloudPlayer,
    val configuredSetting: ConfiguredSetting
) : RespondingNettyPacket<ClientboundSettingModifyResultPacket>()