package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.settings.api.common.Setting
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
        val settingString = SettingSerializer.serialize(encoder, value.setting)
        encoder.encodeString(
            "${value.id}:${value.player.uuid}:$settingString:${value.value}:${value.addedAt}:${value.updatedAt}"
        )
    }

    override fun deserialize(decoder: Decoder): SettingEntry {
        decoder.decodeString().let {
            val parts = it.split(":", limit = 6)
            return object : SettingEntry {
                override val id: Long
                    get() = parts[0].toLong()

                override val player: OfflineCloudPlayer
                    get() = OfflineCloudPlayer[UUID.fromString(parts[1])]

                override val setting: Setting
                    get() = SettingSerializer.decode(parts[2])

                override var value: String
                    get() = parts[3]
                    set(_) {}

                override val addedAt: Long
                    get() = parts[4].toLong()

                override val updatedAt: Long
                    get() = parts[5].toLong()
            }
        }
    }
}
