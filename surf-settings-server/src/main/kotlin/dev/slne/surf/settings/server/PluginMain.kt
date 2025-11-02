package dev.slne.surf.settings.server

import dev.slne.surf.cloud.api.server.plugin.StandalonePlugin
import dev.slne.surf.cloud.api.server.plugin.utils.currentDb
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import dev.slne.surf.settings.server.database.table.SettingsTable
import dev.slne.surf.surfapi.core.api.util.logger
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class PluginMain : StandalonePlugin() {
    private val _logger = logger()
    override suspend fun load() {
    }

    override suspend fun enable() {
        _logger.atInfo()
            .log("surf-settings/enable: Development mode detected, initializing database schema...")

        transaction(currentDb) {
            SchemaUtils.create(SettingsTable, SettingsEntryTable)
        }

        _logger.atInfo().log("surf-settings/enable: Database schema initialized.")
    }

    override suspend fun disable() {
    }
}