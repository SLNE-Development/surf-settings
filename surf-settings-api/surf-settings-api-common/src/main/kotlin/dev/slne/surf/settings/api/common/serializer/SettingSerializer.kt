package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.surfSettingApi
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
object SettingSerializer : KSerializer<Setting> {
    override val descriptor = PrimitiveSerialDescriptor("Setting", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Setting
    ) {
        encoder.encodeString("${value.identifier}:${value.category}:${value.displayName}:${value.description}:${value.defaultValue}")
    }

    override fun deserialize(decoder: Decoder): Setting {
        return decode(decoder.decodeString())
    }

    fun encode(value: Setting): String {
        return "${value.identifier}:${value.category}:${value.displayName}:${value.description}:${value.defaultValue}"
    }

    fun decode(value: String): Setting {
        val parts = value.split(":")
        return surfSettingApi.buildSetting(parts[0], parts[1], parts[2], parts[3], parts[4])
    }
}