package dev.slne.surf.settings.core.client.platform

import dev.slne.surf.api.core.util.requiredService
import kotlinx.coroutines.CoroutineScope
import java.util.*

private val platform = requiredService<SettingsPlatform>()

interface SettingsPlatform {
    fun openSettingsGui(playerUuid: UUID)
    fun launchAsync(block: suspend CoroutineScope.() -> Unit)

    companion object : SettingsPlatform by platform
}
