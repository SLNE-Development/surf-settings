package dev.slne.surf.settings.paper

import com.google.auto.service.AutoService
import dev.slne.surf.settings.core.common.SettingsInstance
import dev.slne.surf.settings.core.paper.PaperLoader
import dev.slne.surf.settings.core.paper.PaperSettingsInstance
import net.kyori.adventure.util.Services

@AutoService(SettingsInstance::class)
class PaperSettingsInstanceImpl : PaperSettingsInstance, Services.Fallback {
    override val paperLoader = PaperLoader(plugin.dataPath)
}