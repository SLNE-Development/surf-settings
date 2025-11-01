@file:Suppress("UnstableApiUsage")

package dev.slne.surf.settings.paper.command.dialog

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.cloud.api.common.util.toObjectSet
import dev.slne.surf.settings.api.common.surfSettingApi
import dev.slne.surf.settings.paper.plugin
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import it.unimi.dsi.fastutil.objects.ObjectSet

fun settingCategoriesDialog(categories: ObjectSet<String>) = dialog {
    base {
        title {
            primary("Einstellungen".toSmallCaps())
        }

        body {
            if (categories.isEmpty()) {
                plainMessage {
                    error("Es sind keine Einstellungen verfügbar.".toSmallCaps())
                }
            } else {
                plainMessage {
                    info("Passe deine persönlichen Einstellungen an, um dein Spielerlebnis zu verbessern.")
                }
            }
        }
    }

    type {
        multiAction {
            categories.forEach { cat ->
                action {
                    label {
                        primary(cat)
                    }

                    action {
                        playerCallback { player ->
                            plugin.launch {
                                val entries = surfSettingApi.getEntries(player.uniqueId, cat)
                                settingEntriesDialog(
                                    cat,
                                    entries.map { it.setting to it }.toObjectSet()
                                )
                            }
                        }
                    }
                }
            }

            exitAction {
                label {
                    error("Schließen")
                }

                action {
                    playerCallback {
                        it.closeDialog()
                    }
                }
            }
        }
    }
}