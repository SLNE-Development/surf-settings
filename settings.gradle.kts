rootProject.name = "surf-settings"

include("surf-settings-api")
include("surf-settings-core:surf-settings-core-common")
include("surf-settings-core:surf-settings-core-paper")
include("surf-settings-paper")
include("surf-settings-microservice")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://reposilite.slne.dev/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.slne.surf.api.gradle.settings") version "+"
}
