package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
object SettingCategorySerializer : KSerializer<SettingCategory> {
    override val descriptor = PrimitiveSerialDescriptor("SettingCategory", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: SettingCategory
    ) {
        encoder.encodeString("${value.identifier}:${value.displayName}:${value.description}")
    }

    override fun deserialize(decoder: Decoder): SettingCategory {
        return decode(decoder.decodeString())
    }

    fun decode(value: String): SettingCategory {
        val parts = value.split(":")
        return object : SettingCategory {
            override val identifier: String
                get() = parts[0]
            override val displayName: String
                get() = parts[1]
            override val description: String
                get() = parts[2]
        }
    }
}
