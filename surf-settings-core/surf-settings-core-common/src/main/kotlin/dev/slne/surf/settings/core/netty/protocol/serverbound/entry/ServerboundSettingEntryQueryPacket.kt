package dev.slne.surf.settings.core.netty.protocol.serverbound.entry

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryResultPacket
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SurfNettyPacket("setting:serverbound:setting_entry_query", PacketFlow.SERVERBOUND)
class ServerboundSettingEntryQueryPacket(
    val player: @Contextual UUID,
    val setting: Setting
) : RespondingNettyPacket<ClientboundSettingEntryQueryResultPacket>()