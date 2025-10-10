package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.settings.api.common.result.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.SettingCreateResult
import dev.slne.surf.settings.api.common.result.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.SettingQueryResult
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.*
import dev.slne.surf.settings.core.setting.CommonSettingBridge
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
}