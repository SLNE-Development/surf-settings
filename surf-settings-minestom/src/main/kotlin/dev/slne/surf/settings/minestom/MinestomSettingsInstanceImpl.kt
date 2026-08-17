package dev.slne.surf.settings.minestom

import com.google.auto.service.AutoService
import dev.slne.surf.settings.core.client.ClientLoader
import dev.slne.surf.settings.core.client.ClientSettingsInstance
import dev.slne.surf.settings.core.common.SettingsInstance
import net.kyori.adventure.util.Services

@AutoService(SettingsInstance::class)
class MinestomSettingsInstanceImpl : ClientSettingsInstance, Services.Fallback {
    override val clientLoader = ClientLoader(SurfSettingsMinestomEntrypoint.dataPath)
}
