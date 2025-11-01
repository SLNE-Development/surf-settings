@file:Suppress("UnstableApiUsage")

package dev.slne.surf.settings.paper.command.dialog

import dev.slne.surf.settings.api.common.Setting
import dev.slne.surf.settings.api.common.SettingEntry
import dev.slne.surf.settings.paper.command.util.isBoolean
import dev.slne.surf.settings.paper.command.util.isNumber
import dev.slne.surf.settings.paper.command.util.isText
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import it.unimi.dsi.fastutil.objects.ObjectSet

fun settingEntriesDialog(
    category: String,
    entries: ObjectSet<Pair<Setting, SettingEntry>>
) = dialog {
    base {
        title {
            primary("Einstellungen in $category".toSmallCaps())
        }

        if (entries.isEmpty()) {
            body {
                error("In dieser Kategorie gibt es keine Einstellungen.")
            }
        }

        entries.sortedByDescending { it.first.isText() }.forEach {
            val setting = it.first
            val entry = it.second

            input {
                if (setting.isBoolean()) {
                    simpleBoolean(setting.identifier, entry.value.toBooleanStrict()) {
                        primary(setting.displayName)
                    }
                } else if (setting.isNumber()) {
                    numberRange(setting.identifier, 1..100) {
                        label {
                            primary(setting.displayName)
                        }
                        step(1f)
                        initial(entry.value.toFloatOrNull() ?: 0f)
                    }
                } else {
                    text(setting.identifier) {
                        label {
                            primary(setting.displayName)
                        }

                        initial(entry.value)
                    }
                }
            }
        }
    }

    type {
        multiAction {

        }
    }
}