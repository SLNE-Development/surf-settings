package dev.slne.surf.settings.core.bridge

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.bridge.InternalSettingBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.impl.SettingImpl
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

    override fun deserialize(decoder: Decoder) = SettingImpl.serializer().deserialize(decoder)
}