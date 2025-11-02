package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundSettingCreateResultPacket
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SurfNettyPacket("setting:serverbound:setting_create", PacketFlow.SERVERBOUND)
class ServerboundSettingCreatePacket(
    val uid: @Contextual UUID,
    val identifier: String,
    val category: String,
    val displayName: String,
    val description: String,
    val defaultValue: String
) : RespondingNettyPacket<ClientboundSettingCreateResultPacket>()