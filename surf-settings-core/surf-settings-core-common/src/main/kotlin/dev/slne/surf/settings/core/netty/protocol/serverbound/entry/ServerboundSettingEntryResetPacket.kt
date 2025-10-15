package dev.slne.surf.settings.core.netty.protocol.serverbound.entry

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.core.netty.protocol.clientbound.entry.ClientboundSettingEntryResetResultPacket
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SurfNettyPacket("setting:serverbound:setting_entry_reset", PacketFlow.SERVERBOUND)
class ServerboundSettingEntryResetPacket(
    val player: @Contextual UUID,
    val setting: Setting
) : RespondingNettyPacket<ClientboundSettingEntryResetResultPacket>()