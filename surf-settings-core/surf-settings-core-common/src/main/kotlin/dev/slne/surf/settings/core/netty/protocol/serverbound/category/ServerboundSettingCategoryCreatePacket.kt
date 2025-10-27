package dev.slne.surf.settings.core.netty.protocol.serverbound.category

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryCreateResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_category_create", PacketFlow.SERVERBOUND)
class ServerboundSettingCategoryCreatePacket(
    val identifier: String,
    val displayName: String,
    val description: String,
) : RespondingNettyPacket<ClientboundSettingCategoryCreateResultPacket>()