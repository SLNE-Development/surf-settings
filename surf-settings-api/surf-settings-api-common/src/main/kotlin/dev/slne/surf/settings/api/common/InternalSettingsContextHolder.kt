package dev.slne.surf.settings.api.common

import dev.slne.surf.surfapi.core.api.util.requiredService
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import org.springframework.context.ApplicationContext

@InternalSettingsApi
interface InternalSettingsContextHolder {
    val context: ApplicationContext

    companion object {
        val instance = requiredService<InternalSettingsContextHolder>()
    }
}