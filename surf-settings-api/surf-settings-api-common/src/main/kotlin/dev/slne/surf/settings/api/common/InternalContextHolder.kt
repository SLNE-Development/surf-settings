package dev.slne.surf.settings.api.common

import dev.slne.surf.surfapi.core.api.util.requiredService
import dev.slne.surf.settings.api.common.util.InternalApi
import org.springframework.context.ApplicationContext

@InternalApi
interface InternalContextHolder {
    val context: ApplicationContext

    companion object {
        val instance = requiredService<InternalContextHolder>()
    }
}