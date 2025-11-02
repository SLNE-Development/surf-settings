package dev.slne.surf.settings.core.lifecycle

import dev.slne.surf.cloud.api.common.util.forEachAnnotationOrdered
import dev.slne.surf.cloud.api.common.util.forEachAnnotationOrderedReversed
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component

@Component
class SettingsLifecycleManager(private val lifecycles: ObjectProvider<SettingsLifecycle>) {
    fun onBootstrap() {
        lifecycles.forEachAnnotationOrdered { it.onBootstrap() }
    }

    suspend fun onLoad() {
        lifecycles.forEachAnnotationOrdered { it.onLoad() }
    }

    suspend fun onEnable() {
        lifecycles.forEachAnnotationOrdered { it.onEnable() }
    }

    suspend fun onDisable() {
        lifecycles.forEachAnnotationOrderedReversed { it.onDisable() }
    }
}