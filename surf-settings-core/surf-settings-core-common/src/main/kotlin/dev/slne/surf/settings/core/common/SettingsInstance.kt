package dev.slne.surf.settings.core.common

import dev.slne.surf.rabbitmq.api.RabbitMQApi
import dev.slne.surf.surfapi.core.api.util.requiredService

private val instance = requiredService<SettingsInstance>()

interface SettingsInstance {
    val rabbitApi: RabbitMQApi

    companion object : SettingsInstance by instance {
        val INSTANCE get() = instance
    }
}