package dev.slne.surf.settings.core.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.mutableObject2ObjectMapOf
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.api.core.util.toMutableObjectSet
import dev.slne.surf.api.core.util.toObjectSet
import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.core.common.rabbit.packet.request.*
import dev.slne.surf.settings.core.common.service.SettingsService
import dev.slne.surf.settings.core.paper.PaperSettingsInstance
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SettingsService::class)
class SettingServiceImpl : SettingsService, Services.Fallback {
    private val _playerSettings = mutableObject2ObjectMapOf<UUID, ObjectSet<PlayerSetting>>()

    override val settings = mutableObjectSetOf<Setting>()
    override val playerSettings: ObjectSet<PlayerSetting> =
        _playerSettings.values.flatten().toObjectSet()

    override fun getSettingByName(name: String): Setting? = settings.firstOrNull { it.name == name }
    override fun getSettingsForPlayer(playerUuid: UUID): ObjectSet<PlayerSetting> =
        _playerSettings[playerUuid] ?: mutableObjectSetOf()

    override fun getSettingForPlayerOrDefault(
        playerUuid: UUID,
        settingName: String
    ): PlayerSetting? =
        getSettingsForPlayer(playerUuid).firstOrNull { it.setting.name == settingName } ?: run {
            val setting = getSettingByName(settingName) ?: return null
            PlayerSetting(
                setting = setting,
                settingValue = setting.defaultValue
            )
        }

    override fun getSettingForPlayer(playerUuid: UUID, settingName: String): PlayerSetting? =
        getSettingsForPlayer(playerUuid).firstOrNull { it.setting.name == settingName }

    override fun cachePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        val playerSettings =
            _playerSettings.getOrPut(playerUuid) { mutableObjectSetOf() }.toMutableObjectSet()
        playerSettings.removeIf { it.setting.name == playerSetting.setting.name }
        playerSettings.add(playerSetting)

        _playerSettings[playerUuid] = playerSettings
    }

    override suspend fun savePlayerSetting(
        playerUuid: UUID,
        playerSetting: PlayerSetting
    ) {
        PaperSettingsInstance.rabbitApi.sendRequest(
            SavePlayerSettingRequestPacket(
                playerUuid,
                playerSetting
            )
        )
    }

    override suspend fun cachePlayerSettings(playerUuid: UUID) {
        _playerSettings[playerUuid] = PaperSettingsInstance.rabbitApi.sendRequest(
            LoadPlayerSettingsRequestPacket(playerUuid)
        ).playerSettings.mapNotNull {
            val setting = getSettingByName(it.first) ?: return@mapNotNull null
            PlayerSetting(
                setting = setting,
                settingValue = it.second
            )
        }.toObjectSet()
    }

    override fun invalidatePlayerSettingsCache(playerUuid: UUID) {
        _playerSettings.remove(playerUuid)
    }

    override suspend fun refreshSettings() {
        settings.clear()
        settings.addAll(PaperSettingsInstance.rabbitApi.sendRequest(LoadSettingsRequestPacket()).settings)
    }

    override suspend fun createSetting(
        name: String,
        defaultValue: String
    ): Setting {
        val existing = getSettingByName(name)
        if (existing != null) {
            return existing
        }

        return PaperSettingsInstance.rabbitApi.sendRequest(
            CreateSettingRequestPacket(
                name,
                defaultValue
            )
        ).setting.also {
            settings.add(it)
        }
    }

    override suspend fun deleteSetting(name: String) {
        PaperSettingsInstance.rabbitApi.sendRequest(
            DeleteSettingRequestPacket(name)
        ).also {
            settings.removeIf { it.name == name }
        }
    }
}