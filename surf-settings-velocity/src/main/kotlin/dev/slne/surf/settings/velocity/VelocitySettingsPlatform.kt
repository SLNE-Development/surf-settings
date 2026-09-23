package dev.slne.surf.settings.velocity

import com.github.shynixn.mccoroutine.velocity.launch
import com.google.auto.service.AutoService
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import kotlinx.coroutines.CoroutineScope
import java.util.UUID

@AutoService(SettingsPlatform::class)
class VelocitySettingsPlatform : SettingsPlatform {

    override fun openSettingsGui(playerUuid: UUID) {
        // Not implemented on velocity. Fail silently
    }

    override fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        plugin.container.launch {
            block()
        }
    }
}