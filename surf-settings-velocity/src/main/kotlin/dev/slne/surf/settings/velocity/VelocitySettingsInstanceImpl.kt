package dev.slne.surf.settings.velocity

import com.google.auto.service.AutoService
import dev.slne.surf.settings.core.client.ClientLoader
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.common.SettingsInstance

@AutoService(SettingsInstance::class)
class VelocitySettingsInstanceImpl : ClientSettingsInstance {
    override val clientLoader = ClientLoader(plugin.dataPath)
}