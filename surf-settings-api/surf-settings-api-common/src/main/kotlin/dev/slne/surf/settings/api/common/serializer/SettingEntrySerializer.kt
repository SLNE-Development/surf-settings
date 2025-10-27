package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@InternalSettingsApi
object SettingEntrySerializer : KSerializer<SettingEntry> {
    override val descriptor = PrimitiveSerialDescriptor("SettingEntry", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SettingEntry) {
        encoder.encodeString("${value.player.uuid}|${value.settingIdentifier}|${value.value}")
    }

    override fun deserialize(decoder: Decoder): SettingEntry {
        val parts = decoder.decodeString().split("|", limit = 3)
        require(parts.size == 3) { "Invalid SettingEntry format" }

        val uuid = UUID.fromString(parts[0])
        val setting = parts[1]
        val value = parts[2]

        return object : SettingEntry {
            override val player = OfflineCloudPlayer[uuid]
            override val settingIdentifier: String = setting
            override var value: String = value
        }
    }
}
