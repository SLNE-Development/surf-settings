package dev.slne.surf.settings.minestom

import com.google.auto.service.AutoService
import dev.slne.minestom.lobby.api.coroutine.minestomAsyncScope
import dev.slne.minestom.lobby.api.extension.ConnectionManager
import dev.slne.minestom.lobby.api.player.getOnlineLobbyPlayerByUuid
import dev.slne.surf.api.minestom.inventory.framework.open
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.minestom.menu.SettingsMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SettingsPlatform::class)
class MinestomSettingsPlatform : SettingsPlatform, Services.Fallback {
    override fun openSettingsGui(playerUuid: UUID) {
        val player = ConnectionManager.getOnlineLobbyPlayerByUuid(playerUuid) ?: return
        player.closeInventory()
        SettingsMenu.open(player)
    }

    override fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        minestomAsyncScope.launch { block() }
    }
}
