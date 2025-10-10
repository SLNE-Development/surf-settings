package dev.slne.surf.settings.core.netty.protocol.clientbound.entry

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_entry_modify_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingEntryModifyResultPacket(
    val result: SettingEntryModifyResult
) : ResponseNettyPacket()