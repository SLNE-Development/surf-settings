package dev.slne.surf.settings.api.common.dsl

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingCategory
import dev.slne.surf.settings.api.common.surfSettingApi

suspend fun settings(block: suspend SettingsRoot.() -> Unit) {
    SettingsRoot().block()
}

suspend fun setting(block: SingleSettingBuilder.() -> Unit): Setting? {
    val builder = SingleSettingBuilder().apply(block)
    return builder.build()
}

suspend fun category(block: SingleCategoryBuilder.() -> Unit): SettingCategory? {
    val builder = SingleCategoryBuilder().apply(block)
    return builder.build()
}

class SettingsRoot {
    suspend fun category(block: suspend SingleCategoryBuilder.() -> Unit) {
        val builder = SingleCategoryBuilder()
        builder.block()
        builder.build()
    }

    suspend fun setting(block: suspend SingleSettingBuilder.() -> Unit) {
        val builder = SingleSettingBuilder()
        builder.block()
        builder.build()
    }
}


class SingleCategoryBuilder {
    var identifier: String = ""
    var displayName: String = ""
    var description: String = ""

    suspend fun build(): SettingCategory? {
        return surfSettingApi.createCategory(
            identifier = identifier,
            displayName = displayName,
            description = description
        )
    }
}

class SingleSettingBuilder {
    private var categoryId: String = ""
    var identifier: String = ""
    var displayName: String = ""
    var description: String = ""
    var defaultValue: String = ""

    fun withCategory(identifier: String) {
        this.categoryId = identifier
    }

    suspend fun build(): Setting? {
        val category = surfSettingApi.getCategory(categoryId) ?: return null

        return surfSettingApi.createSetting(
            identifier = identifier,
            category = category,
            displayName = displayName,
            description = description,
            defaultValue = defaultValue
        )
    }
}
