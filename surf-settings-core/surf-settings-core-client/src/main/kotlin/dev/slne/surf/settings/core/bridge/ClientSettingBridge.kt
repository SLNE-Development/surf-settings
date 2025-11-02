package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.bridge.InternalSettingBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.*
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ClientSettingBridge : InternalSettingBridge {
    override suspend fun createSetting(
        uid: UUID,
        identifier: String,
        displayName: String,
        description: String,
        category: String,
        defaultValue: String
    ): Setting? = ServerboundSettingCreatePacket(
        uid,
        identifier,
        displayName,
        description,
        category,
        defaultValue
    ).fireAndAwaitOrThrow().result

    override suspend fun getCategories(): ObjectSet<String> =
        ServerboundGetCategoriesPacket().fireAndAwaitOrThrow().result.toObjectSet()

    override suspend fun delete(uid: UUID): Boolean =
        ServerboundSettingDeletePacket(
            uid
        ).fireAndAwaitOrThrow().result

    override suspend fun getSetting(uid: UUID): Setting? =
        ServerboundSettingQueryPacket(
            uid
        ).fireAndAwaitOrThrow().result

    override suspend fun getSetting(identifier: String): Setting? =
        ServerboundSettingQueryByIdPacket(
            identifier
        ).fireAndAwaitOrThrow().result

    override suspend fun all(): ObjectSet<Setting> =
        ServerboundSettingQueryAllPacket().fireAndAwaitOrThrow().result.toObjectSet()
}