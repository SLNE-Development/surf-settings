package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.bridge.InternalSettingCategoryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryCreatePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryDeletePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryAllPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryPacket
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ClientSettingCategoryBridge : InternalSettingCategoryBridge {
    override suspend fun createCategory(
        identifier: String,
        displayName: String,
        description: String
    ): SettingCategory? = ServerboundSettingCategoryCreatePacket(
        identifier,
        displayName,
        description
    ).fireAndAwaitOrThrow().result

    override suspend fun deleteCategory(category: SettingCategory): Boolean =
        ServerboundSettingCategoryDeletePacket(category).fireAndAwaitOrThrow().result

    override suspend fun getCategory(identifier: String): SettingCategory? =
        ServerboundSettingCategoryQueryPacket(identifier).fireAndAwaitOrThrow().result

    override suspend fun all(): ObjectSet<SettingCategory> =
        ServerboundSettingCategoryQueryAllPacket().fireAndAwaitOrThrow().result.toObjectSet()
}