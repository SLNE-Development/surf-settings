package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.bridge.InternalSettingBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingCreatePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingDeletePacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingQueryAllPacket
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.ServerboundSettingQueryPacket
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ClientSettingBridge : InternalSettingBridge {
    override suspend fun createSetting(
        identifier: String,
        category: SettingCategory,
        displayName: String,
        description: String,
        defaultValue: String
    ): Setting? = ServerboundSettingCreatePacket(
        identifier,
        category,
        displayName,
        description,
        defaultValue
    ).fireAndAwaitOrThrow().result

    override suspend fun delete(identifier: String): Boolean =
        ServerboundSettingDeletePacket(
            identifier
        ).fireAndAwaitOrThrow().result

    override suspend fun getSetting(identifier: String): Setting? =
        ServerboundSettingQueryPacket(
            identifier
        ).fireAndAwaitOrThrow().result

    override suspend fun all(): ObjectSet<Setting> =
        ServerboundSettingQueryAllPacket().fireAndAwaitOrThrow().result.toObjectSet()
}