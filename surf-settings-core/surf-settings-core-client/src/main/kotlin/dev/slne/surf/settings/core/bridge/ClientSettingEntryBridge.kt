package dev.slne.surf.settings.core.bridge

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.bridge.InternalSettingEntryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.netty.protocol.serverbound.entry.*
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Component
import java.util.*

@InternalSettingsApi
@Component
class ClientSettingEntryBridge : InternalSettingEntryBridge {
    override suspend fun modify(
        playerUuid: UUID,
        setting: Setting,
        value: String
    ): Boolean = ServerboundSettingEntryModifyPacket(
        playerUuid, setting, value
    ).fireAndAwaitOrThrow().result

    override suspend fun reset(
        playerUUID: UUID,
        setting: Setting
    ): Boolean = ServerboundSettingEntryResetPacket(
        playerUUID, setting
    ).fireAndAwaitOrThrow().result

    override suspend fun getAll(playerUuid: UUID): ObjectSet<SettingEntry> =
        ServerboundSettingEntryQueryAllByPlayerPacket(playerUuid).fireAndAwaitOrThrow().result.toObjectSet()

    override suspend fun getAll(): ObjectSet<SettingEntry> =
        ServerboundSettingEntryQueryAllPacket().fireAndAwaitOrThrow().result.toObjectSet()

    override suspend fun getEntry(
        playerUUID: UUID,
        setting: Setting
    ): SettingEntry? =
        ServerboundSettingEntryQueryPacket(playerUUID, setting).fireAndAwaitOrThrow().result

    override suspend fun getEntries(
        playerUuid: UUID,
        category: String,
        defaults: Boolean
    ): ObjectSet<SettingEntry> = ServerboundSettingEntryQueryByCategoryPacket(
        playerUuid, category
    ).fireAndAwaitOrThrow().result.toObjectSet()
}