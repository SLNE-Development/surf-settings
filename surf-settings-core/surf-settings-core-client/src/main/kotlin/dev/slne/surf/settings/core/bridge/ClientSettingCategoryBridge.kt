package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.result.category.SettingCategoryCreateResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryDeleteResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryQueryResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryCreatePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryDeletePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryAllPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.category.ServerboundSettingCategoryQueryPacket
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ClientSettingCategoryBridge : CommonSettingCategoryBridge() {
    override suspend fun createCategory(category: SettingCategory): SettingCategoryCreateResult =
        ServerboundSettingCategoryCreatePacket(category).fireAndAwaitOrThrow().result

    override suspend fun deleteCategory(category: SettingCategory): SettingCategoryDeleteResult =
        ServerboundSettingCategoryDeletePacket(category).fireAndAwaitOrThrow().result

    override suspend fun queryCategory(identifier: String): SettingCategoryQueryResult =
        ServerboundSettingCategoryQueryPacket(identifier).fireAndAwaitOrThrow().result

    override suspend fun queryAll(): ObjectSet<SettingCategory> =
        ServerboundSettingCategoryQueryAllPacket().fireAndAwaitOrThrow().queries
}