package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingCreateIgnoringResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_create_ignoring", PacketFlow.SERVERBOUND)
class ServerboundSettingCreateIgnoringPacket(
    val setting: Setting
) : RespondingNettyPacket<ClientboundSettingCreateIgnoringResultPacket>()