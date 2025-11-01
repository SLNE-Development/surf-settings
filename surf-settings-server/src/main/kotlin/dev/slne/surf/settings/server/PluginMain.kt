package dev.slne.surf.settings.server

import dev.slne.surf.cloud.api.server.plugin.StandalonePlugin
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import dev.slne.surf.settings.server.database.table.SettingsTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PluginMain : StandalonePlugin() {
    override suspend fun load() {
        newSuspendedTransaction {
            SchemaUtils.create(
                SettingsTable,
                SettingsEntryTable
            ) //TODO: REMOVE FOR PRODUCTION
        }
    }

    override suspend fun enable() {
    }

    override suspend fun disable() {
    }
}