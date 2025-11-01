package dev.slne.surf.settings.api.common.dsl

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.surfSettingApi
import java.util.*

suspend fun settings(block: suspend SettingsRoot.() -> Unit) {
    SettingsRoot().block()
}

suspend fun setting(block: SingleSettingBuilder.() -> Unit): Setting? {
    val builder = SingleSettingBuilder().apply(block)
    return builder.build()
}

class SettingsRoot {
    suspend fun setting(block: suspend SingleSettingBuilder.() -> Unit) {
        val builder = SingleSettingBuilder()
        builder.block()
        builder.build()
    }
}

class SingleSettingBuilder {
    var identifier: String = ""
    var category: String = ""
    var displayName: String = ""
    var description: String = ""
    var defaultValue: String = ""

    suspend fun build(): Setting? {
        return surfSettingApi.createSetting(
            uid = UUID.randomUUID(),
            identifier = identifier,
            category = category,
            displayName = displayName,
            description = description,
            defaultValue = defaultValue
        )
    }
}
