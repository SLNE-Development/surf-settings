package dev.slne.surf.settings.core.common

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.rabbitmq.api.RabbitMQApi

private val instance = requiredService<SettingsInstance>()

interface SettingsInstance {
    val rabbitApi: RabbitMQApi

    companion object : SettingsInstance by instance {
        val INSTANCE get() = instance
    }
}