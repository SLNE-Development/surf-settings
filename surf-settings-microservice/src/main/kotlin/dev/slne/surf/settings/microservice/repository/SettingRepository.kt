package dev.slne.surf.settings.microservice.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.deleteWhere
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.microservice.table.SettingsTable
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet

val settingRepository = SettingRepository()

class SettingRepository {
    suspend fun loadSettings(): ObjectSet<Setting> = suspendTransaction {
        SettingsTable.selectAll().map {
            Setting(
                name = it[SettingsTable.name],
                defaultValue = it[SettingsTable.defaultValue]
            )
        }.toSet().toObjectSet()
    }

    suspend fun createSetting(name: String, defaultValue: String): Setting = suspendTransaction {
        SettingsTable.upsert {
            it[SettingsTable.name] = name
            it[SettingsTable.defaultValue] = defaultValue
        }

        Setting(name, defaultValue)
    }

    suspend fun deleteSetting(name: String) = suspendTransaction {
        SettingsTable.deleteWhere { SettingsTable.name eq name }
    }
}