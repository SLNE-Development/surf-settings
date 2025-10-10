package dev.slne.surf.settings.core.netty.protocol.clientbound

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.setting.Setting
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_query_many", PacketFlow.CLIENTBOUND)
data class ClientboundSettingQueryManyPacket(
    val queries: ObjectSet<Setting>
) : ResponseNettyPacket()