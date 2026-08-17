package dev.slne.surf.settings.core.client

import dev.slne.surf.rabbitmq.api.ClientRabbitMQApi
import dev.slne.surf.settings.core.common.SettingsInstance

interface ClientSettingsInstance : SettingsInstance {
    val clientLoader: ClientLoader

    override val rabbitApi: ClientRabbitMQApi get() = clientLoader.rabbitApi

    companion object : ClientSettingsInstance by SettingsInstance.INSTANCE as ClientSettingsInstance {
        val INSTANCE get() = SettingsInstance.INSTANCE
    }
}
