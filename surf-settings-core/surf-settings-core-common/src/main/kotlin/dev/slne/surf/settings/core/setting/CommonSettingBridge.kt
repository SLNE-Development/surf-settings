package dev.slne.surf.settings.core.setting

import dev.slne.surf.settings.api.common.setting.InternalSettingBridge
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
abstract class CommonSettingBridge : InternalSettingBridge {
    override val descriptor: SerialDescriptor
        get() = SettingImpl.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Setting) {
        require(value is SettingImpl) { "Value must be of type SettingImpl" }
        SettingImpl.serializer().serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): Setting {
        return SettingImpl.serializer().deserialize(decoder)
    }
}