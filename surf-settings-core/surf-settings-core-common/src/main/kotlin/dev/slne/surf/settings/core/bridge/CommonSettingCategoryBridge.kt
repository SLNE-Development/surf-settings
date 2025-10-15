package dev.slne.surf.settings.core.bridge

import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.bridge.InternalSettingCategoryBridge
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import dev.slne.surf.settings.core.impl.SettingCategoryImpl
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
abstract class CommonSettingCategoryBridge : InternalSettingCategoryBridge {
    override val descriptor: SerialDescriptor
        get() = SettingCategoryImpl.serializer().descriptor

    override fun serialize(encoder: Encoder, value: SettingCategory) {
        require(value is SettingCategoryImpl) { "Value must be of type SettingCategoryImpl" }
        SettingCategoryImpl.serializer().serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder) =
        SettingCategoryImpl.serializer().deserialize(decoder)
}