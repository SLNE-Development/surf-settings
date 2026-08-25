package dev.slne.surf.settings.paper.menu

import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import me.devnatan.inventoryframework.context.SlotClickContext
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Sound

fun SlotClickContext.playClickSound() {
    this.player.playSound(true) {
        type(Sound.UI_BUTTON_CLICK)
    }
}

private val LOCAL_COLOR: TextColor = TextColor.color(0x42f590)

fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), LOCAL_COLOR, *decoration)