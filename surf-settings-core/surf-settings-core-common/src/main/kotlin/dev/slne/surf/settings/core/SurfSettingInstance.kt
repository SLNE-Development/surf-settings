package dev.slne.surf.settings.core

import dev.slne.surf.settings.core.lifecycle.SettingsLifecycleManager
import org.springframework.beans.factory.getBean

object SurfSettingInstance {
    fun onBootstrap() {
        SettingsContextHolderImpl.instance.context.getBean<SettingsLifecycleManager>().onBootstrap()
    }

    val context by lazy {
        SettingsContextHolderImpl.instance.context
    }

    suspend fun onLoad() {
        context.getBean<SettingsLifecycleManager>().onLoad()
    }

    suspend fun onEnable() {
        context.getBean<SettingsLifecycleManager>().onEnable()
    }

    suspend fun onDisable() {
        context.getBean<SettingsLifecycleManager>().onDisable()
    }
}