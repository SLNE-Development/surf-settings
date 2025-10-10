package dev.slne.surf.settings.core.setting.entry

import dev.slne.surf.settings.api.common.setting.entry.InternalSettingEntryBridge
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@InternalSettingsApi
abstract class CommonSettingEntryBridge : InternalSettingEntryBridge {
    override val descriptor: SerialDescriptor
        get() = SettingEntryImpl.serializer().descriptor

    override fun serialize(encoder: Encoder, value: SettingEntry) {
        require(value is SettingEntryImpl) { "Value must be of type SettingEntryImpl" }
        SettingEntryImpl.serializer().serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): SettingEntry {
        return SettingEntryImpl.serializer().deserialize(decoder)
    }
}