plugins {
    id("dev.slne.surf.api.gradle.velocity")
}

surfVelocityApi {
    withCoreVelocity()
}

dependencies {
    api(projects.surfSettingsCore.surfSettingsCoreClient)
}

velocityPluginFile {
    main = "dev.slne.surf.settings.velocity.VelocityMain"
}