package dev.slne.surf.settings.core.netty.protocol.clientbound.category

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.settings.api.common.result.category.SettingCategoryDeleteResult
import kotlinx.serialization.Serializable

@Serializable
@SurfNettyPacket("setting:clientbound:setting_category_delete_result", PacketFlow.CLIENTBOUND)
data class ClientboundSettingCategoryDeleteResultPacket(
    val result: SettingCategoryDeleteResult
) : ResponseNettyPacket()