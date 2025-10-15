package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.result.setting.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateResult
import dev.slne.surf.settings.api.common.result.setting.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.setting.SettingQueryResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.setting.*
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component

@InternalSettingsApi
@Component
class ClientSettingBridge : CommonSettingBridge() {
    override suspend fun createSetting(setting: Setting): SettingCreateResult =
        ServerboundSettingCreatePacket(
            setting
        ).fireAndAwaitOrThrow().result

    override suspend fun createIfNotExists(setting: Setting): SettingCreateIgnoringResult =
        ServerboundSettingCreateIgnoringPacket(
            setting
        ).fireAndAwaitOrThrow().result

    override suspend fun delete(identifier: String): SettingDeleteResult =
        ServerboundSettingDeletePacket(
            identifier
        ).fireAndAwaitOrThrow().result

    override suspend fun query(identifier: String): SettingQueryResult =
        ServerboundSettingQueryPacket(
            identifier
        ).fireAndAwaitOrThrow().result

    override suspend fun queryAll(): ObjectSet<Setting> =
        ServerboundSettingQueryAllPacket().fireAndAwaitOrThrow().queries

    override suspend fun queryByCategory(category: String): ObjectSet<Setting> =
        ServerboundSettingQueryByCategoryPacket(category).fireAndAwaitOrThrow().queries
}