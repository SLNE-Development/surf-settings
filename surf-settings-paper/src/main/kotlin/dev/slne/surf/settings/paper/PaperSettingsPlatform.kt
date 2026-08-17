package dev.slne.surf.settings.paper

import com.github.shynixn.mccoroutine.folia.launch
import com.google.auto.service.AutoService
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.settings.core.client.platform.SettingsPlatform
import dev.slne.surf.settings.paper.menu.SettingsMenu
import kotlinx.coroutines.CoroutineScope
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(SettingsPlatform::class)
class PaperSettingsPlatform : SettingsPlatform, Services.Fallback {
    override fun openSettingsGui(playerUuid: UUID) {
        Bukkit.getPlayer(playerUuid)?.let { player ->
            player.closeInventory()
            SettingsMenu.open(player)
        }
    }

    override fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        plugin.launch { block() }
    }
}
