package dev.slne.surf.settings.core.netty.protocol.clientbound.entry

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_entry_query_many_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingEntryQueryManyPacket(
    val queries: ObjectSet<SettingEntry>
) : ResponseNettyPacket()