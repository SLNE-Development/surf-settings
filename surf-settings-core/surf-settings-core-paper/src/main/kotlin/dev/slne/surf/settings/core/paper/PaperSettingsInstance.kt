package dev.slne.surf.settings.core.paper

import dev.slne.surf.rabbitmq.api.ClientRabbitMQApi
import dev.slne.surf.settings.core.common.SettingsInstance

interface PaperSettingsInstance : SettingsInstance {
    val paperLoader: PaperLoader

    override val rabbitApi: ClientRabbitMQApi get() = paperLoader.rabbitApi

    companion object : PaperSettingsInstance by SettingsInstance.INSTANCE as PaperSettingsInstance {
        val INSTANCE get() = SettingsInstance.INSTANCE
    }
}