package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryResetResult
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.entry.*
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ClientSettingEntryBridge : CommonSettingEntryBridge() {
    override suspend fun modify(
        playerUuid: UUID,
        settingEntry: SettingEntry
    ): SettingEntryModifyResult = ServerboundSettingEntryModifyPacket(
        playerUuid, settingEntry
    ).fireAndAwaitOrThrow().result

    override suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ): SettingEntryResetResult = ServerboundSettingEntryResetPacket(
        playerUUID, setting
    ).fireAndAwaitOrThrow().result

    override suspend fun all(playerUuid: UUID): ObjectSet<SettingEntry> =
        ServerboundSettingEntryQueryAllByPlayerPacket(playerUuid).fireAndAwaitOrThrow().queries

    override suspend fun all(): ObjectSet<SettingEntry> =
        ServerboundSettingEntryQueryAllPacket().fireAndAwaitOrThrow().queries

    override suspend fun query(
        playerUUID: UUID,
        setting: Setting
    ): SettingEntryQueryResult =
        ServerboundSettingEntryQueryPacket(playerUUID, setting).fireAndAwaitOrThrow().result
}