package dev.slne.surf.settings.api.common.serializer

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
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
        encoder.encodeString("${value.id}:${value.identifier}:${value.category.identifier}:${value.category.displayName}:${value.category.description}:${value.displayName}:${value.description}:${value.defaultValue}")
    }

    override fun deserialize(decoder: Decoder): Setting {
        return decode(decoder.decodeString())
    }

    fun encode(value: Setting): String {
        return "${value.id}:${value.identifier}:${value.category.identifier}:${value.category.displayName}:${value.category.description}:${value.displayName}:${value.description}:${value.defaultValue}"
    }

    fun decode(value: String): Setting {
        val parts = value.split(":")
        return object : Setting {
            override val id: Long
                get() = parts[0].toLong()
            override val identifier: String
                get() = parts[1]
            override val category: SettingCategory
                get() = object : SettingCategory {
                    override val identifier: String
                        get() = parts[2]
                    override val displayName: String
                        get() = parts[3]
                    override val description: String
                        get() = parts[4]
                }
            override val displayName: String
                get() = parts[5]
            override val description: String
                get() = parts[6]
            override val defaultValue: String
                get() = parts[7]
        }
    }
}