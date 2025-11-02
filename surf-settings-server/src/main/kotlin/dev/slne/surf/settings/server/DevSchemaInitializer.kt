package dev.slne.surf.settings.server

import dev.slne.surf.cloud.api.server.plugin.utils.currentDb
import dev.slne.surf.settings.core.lifecycle.SettingsLifecycle
import dev.slne.surf.settings.server.database.table.SettingsEntryTable
import dev.slne.surf.settings.server.database.table.SettingsTable
import dev.slne.surf.surfapi.core.api.util.logger
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(name = ["DEVELOPMENT"], havingValue = "true")
class DevSchemaInitializer : SettingsLifecycle {
    private val logger = logger()

    override suspend fun onLoad() {
        logger.atInfo().log("Development mode detected, initializing database schema...")

        transaction(currentDb) {
            SchemaUtils.create(SettingsTable, SettingsEntryTable)
        }

        logger.atInfo().log("Database schema initialized.")
    }
}