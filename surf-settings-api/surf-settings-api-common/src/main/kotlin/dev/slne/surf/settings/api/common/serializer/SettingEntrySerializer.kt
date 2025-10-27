package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
object SettingEntrySerializer : KSerializer<SettingEntry> {
    override val descriptor = PrimitiveSerialDescriptor("SettingEntry", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SettingEntry) {
        encoder.encodeString("${value.settingIdentifier}|${value.value}")
    }

    override fun deserialize(decoder: Decoder): SettingEntry {
        val parts = decoder.decodeString().split("|", limit = 2)
        require(parts.size == 2) { "Invalid SettingEntry format" }

        val setting = parts[0]
        val value = parts[1]

        return object : SettingEntry {
            override val settingIdentifier: String = setting
            override var value: String = value
        }
    }
}
