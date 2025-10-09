package dev.slne.surf.settings.core

import com.google.auto.service.AutoService
import dev.slne.surf.settings.api.common.InternalSettingsContextHolder
import dev.slne.surf.settings.api.common.util.InternalSettingsApi
import org.springframework.context.ApplicationContext

@OptIn(InternalSettingsApi::class)
@AutoService(InternalSettingsContextHolder::class)
class SettingsContextHolderImpl : InternalSettingsContextHolder {
    override lateinit var context: ApplicationContext

    companion object {
        val instance = InternalSettingsContextHolder.instance as SettingsContextHolderImpl
    }
}