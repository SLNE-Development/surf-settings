package dev.slne.surf.settings.core.netty.protocol.serverbound.category

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.core.netty.protocol.clientbound.category.ClientboundSettingCategoryDeleteResultPacket
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:serverbound:setting_category_delete", PacketFlow.SERVERBOUND)
class ServerboundSettingCategoryDeletePacket(
    val category: SettingCategory
) : RespondingNettyPacket<ClientboundSettingCategoryDeleteResultPacket>()