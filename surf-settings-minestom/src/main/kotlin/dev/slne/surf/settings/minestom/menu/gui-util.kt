package dev.slne.surf.settings.minestom.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.minestom.builder.buildItem
import me.devnatan.inventoryframework.context.SlotClickContext
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.sound.SoundEvent
import net.minestom.server.entity.Player

fun SlotClickContext.playClickSound() {
    player.playSound(CLICK_SOUND, Sound.Emitter.self())
}

fun Player.sendSettingChanged(prefix: String, state: Boolean) {
    sendText {
        appendSuccessPrefix()
        success(prefix)
        variableValue(if (state) "aktiviert" else "deaktiviert")
        success(".")
    }
}

private val LOCAL_COLOR: TextColor = TextColor.color(0x42f590)

fun SurfComponentBuilder.localColored(
    text: Any,
    vararg decoration: TextDecoration
) = text(text.toString(), LOCAL_COLOR, *decoration)

fun menuItem(material: Material, name: String): ItemStack = buildItem(material) {
    displayName {
        localColored(name.toSmallCaps(), TextDecoration.BOLD)
    }
}

fun settingItem(
    material: Material,
    name: String,
    description: String,
    state: Boolean,
    warnings: List<String> = emptyList()
): ItemStack = buildItem(material) {
    displayName {
        localColored(name.toSmallCaps(), TextDecoration.BOLD)
    }
    lore(*settingLore(description, state, warnings).toTypedArray())
}

private fun settingLore(
    description: String,
    state: Boolean,
    warnings: List<String>
): List<Component> = buildList {
    add(Component.empty())
    add(buildText { variableValue("Beschreibung:".toSmallCaps()) })
    add(buildText { localColored(description) })
    add(Component.empty())
    add(buildText { variableValue("Status:".toSmallCaps()) })
    add(buildText {
        spacer("-")
        appendSpace()
        localColored(if (state) "Aktiviert" else "Deaktiviert")
    })
    add(Component.empty())
    add(buildText { spacer("Klicke, um die Einstellung zu ändern") })

    if (warnings.isNotEmpty()) {
        add(Component.empty())
        warnings.forEach { warning ->
            add(buildText { error(warning) })
        }
    }
}

private val CLICK_SOUND = Sound.sound(
    SoundEvent.UI_BUTTON_CLICK,
    Sound.Source.PLAYER,
    1f,
    1f
)
