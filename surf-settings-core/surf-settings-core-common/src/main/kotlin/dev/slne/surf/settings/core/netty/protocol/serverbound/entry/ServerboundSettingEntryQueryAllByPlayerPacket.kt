package dev.slne.surf.settings.core.netty.protocol.serverbound.entry

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryQueryManyPacket
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SurfNettyPacket("setting:serverbound:setting_entry_query_all_player", PacketFlow.SERVERBOUND)
class ServerboundSettingEntryQueryAllByPlayerPacket(
    val playerUuid: @Contextual UUID
) : RespondingNettyPacket<ClientboundSettingEntryQueryManyPacket>()