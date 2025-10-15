package dev.slne.surf.settings.server.service

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.result.setting.SettingCreateIgnoringResult
import dev.slne.surf.settings.api.common.result.setting.SettingCreateResult
import dev.slne.surf.settings.api.common.result.setting.SettingDeleteResult
import dev.slne.surf.settings.api.common.result.setting.SettingQueryResult
import dev.slne.surf.settings.server.repository.SettingRepository
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Service

@Service
class SettingService(
    private val settingRepository: SettingRepository
) {
    suspend fun createSetting(setting: Setting): SettingCreateResult {
        val create = settingRepository.create(setting) ?: return SettingCreateResult.Failure(
            SettingCreateResult.SettingCreateFailureReason.SETTING_ALREADY_EXISTS
        )

        return SettingCreateResult.Success(create)
    }

    suspend fun createIfNotExists(setting: Setting): SettingCreateIgnoringResult {
        val create = settingRepository.createIfNotExists(setting)
            ?: return SettingCreateIgnoringResult.Failure(
                SettingCreateIgnoringResult.SettingCreateIgnoringFailureReason.INTERNAL_ERROR
            )

        return SettingCreateIgnoringResult.Success(create)
    }

    suspend fun delete(identifier: String) = if (settingRepository.delete(identifier)) {
        SettingDeleteResult.Success()
    } else {
        SettingDeleteResult.Failure(SettingDeleteResult.SettingDeleteFailureReason.SETTING_NOT_FOUND)
    }

    suspend fun query(identifier: String): SettingQueryResult {
        val query = settingRepository.query(identifier)
            ?: return SettingQueryResult.Failure(SettingQueryResult.SettingQueryFailureReason.SETTING_NOT_FOUND)

        return SettingQueryResult.Success(query)
    }

    suspend fun queryByCategory(category: String): ObjectSet<Setting> =
        settingRepository.queryByCategory(category)

    suspend fun queryAll(): ObjectSet<Setting> = settingRepository.all()
}