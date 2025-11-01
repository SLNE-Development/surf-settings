package dev.slne.surf.settings.paper.command.util

import dev.slne.surf.settings.api.common.Setting

fun Setting.isBoolean(): Boolean =
    defaultValue.equals("true", ignoreCase = true) ||
            defaultValue.equals("false", ignoreCase = true)

fun Setting.isNumber(): Boolean =
    defaultValue.toDoubleOrNull() != null


fun Setting.isText(): Boolean =
    !isBoolean() && !isNumber()