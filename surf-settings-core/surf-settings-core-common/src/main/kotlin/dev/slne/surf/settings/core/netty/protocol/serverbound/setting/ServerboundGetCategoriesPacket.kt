package dev.slne.surf.settings.core.netty.protocol.serverbound.setting

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.core.netty.protocol.clientbound.setting.ClientboundCategoriesResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:categories_get", PacketFlow.SERVERBOUND)
class ServerboundGetCategoriesPacket : RespondingNettyPacket<ClientboundCategoriesResultPacket>()