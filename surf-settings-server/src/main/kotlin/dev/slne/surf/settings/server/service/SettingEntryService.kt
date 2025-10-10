package dev.slne.surf.settings.server.service

import dev.slne.surf.settings.api.common.result.entry.SettingEntryModifyResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryQueryResult
import dev.slne.surf.settings.api.common.result.entry.SettingEntryResetResult
import dev.slne.surf.settings.api.common.setting.Setting
import dev.slne.surf.settings.api.common.setting.entry.SettingEntry
import dev.slne.surf.settings.server.repository.SettingEntryRepository
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.springframework.stereotype.Service
import java.util.*

@Service
class SettingEntryService(
    private val settingEntryRepository: SettingEntryRepository
) {
    suspend fun modify(playerUuid: UUID, settingEntry: SettingEntry): SettingEntryModifyResult {
        val entry = settingEntryRepository.modify(playerUuid, settingEntry)
        return if (entry != null) {
            SettingEntryModifyResult.Success(entry)
        } else {
            SettingEntryModifyResult.Failure(SettingEntryModifyResult.SettingModifyResultFailure.SETTING_NOT_FOUND)
        }
    }

    suspend fun reset(playerUUID: UUID, setting: Setting): SettingEntryResetResult {
        val entry = settingEntryRepository.reset(playerUUID, setting)?.toDto()
        return if (entry != null) {
            SettingEntryResetResult.Success(entry)
        } else {
            SettingEntryResetResult.Failure(SettingEntryResetResult.SettingEntryResetFailureReason.SETTING_NOT_FOUND)
        }
    }

    suspend fun all(playerUuid: UUID): ObjectSet<SettingEntry> =
        settingEntryRepository.all(playerUuid)

    suspend fun all(): ObjectSet<SettingEntry> = settingEntryRepository.all()

    suspend fun query(playerUUID: UUID, setting: Setting): SettingEntryQueryResult {
        val entry = settingEntryRepository.query(playerUUID, setting)?.toDto()
        return if (entry != null) {
            SettingEntryQueryResult.Success(entry)
        } else {
            SettingEntryQueryResult.Failure(SettingEntryQueryResult.SettingEntryQueryFailureReason.SETTING_NOT_FOUND)
        }
    }
}