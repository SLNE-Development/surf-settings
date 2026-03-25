rootProject.name = "surf-settings"

include("surf-settings-api")
include("surf-settings-paper")
include("surf-settings-microservice")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.slne.dev/repository/maven-public/") { name = "maven-public" }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.slne.surf.surfapi.gradle.settings") version "1.21.11+"
}
include("surf-settings-core:surf-settings-core-common")
include("surf-settings-core:surf-settings-core-paper")