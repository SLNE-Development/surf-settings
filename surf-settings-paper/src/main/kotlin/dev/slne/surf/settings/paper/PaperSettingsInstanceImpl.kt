package dev.slne.surf.settings.paper

import com.google.auto.service.AutoService
import dev.slne.surf.settings.core.common.SettingsInstance
import dev.slne.surf.settings.core.client.ClientLoader
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import net.kyori.adventure.util.Services

@AutoService(SettingsInstance::class)
class PaperSettingsInstanceImpl : ClientSettingsInstance, Services.Fallback {
    override val clientLoader = ClientLoader(plugin.dataPath)
}
