package dev.slne.surf.settings.api.common.dsl

import dev.slne.surf.settings.api.common.result.category.SettingCategoryCreateResult
import dev.slne.surf.settings.api.common.result.category.SettingCategoryQueryResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.surfSettingApi

suspend fun settings(block: suspend SettingsRoot.() -> Unit) {
    SettingsRoot().block()
}

suspend fun setting(block: SingleSettingBuilder.() -> Unit): SettingCreateIgnoringResult {
    val builder = SingleSettingBuilder().apply(block)
    return builder.build()
}

suspend fun category(block: SingleCategoryBuilder.() -> Unit): SettingCategoryCreateResult {
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

    suspend fun build(): SettingCategoryCreateResult {
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

    suspend fun build(): SettingCreateIgnoringResult {
        val categoryResult = surfSettingApi.queryCategory(categoryId)

        if (categoryResult.isFailure()) {
            return SettingCreateIgnoringResult.Failure(SettingCreateIgnoringResult.SettingCreateIgnoringFailureReason.CATEGORY_NOT_FOUND)
        }

        return surfSettingApi.createSettingIfNotExists(
            identifier = identifier,
            category = (categoryResult as SettingCategoryQueryResult.Success).category,
            displayName = displayName,
            description = description,
            defaultValue = defaultValue
        )
    }
}
