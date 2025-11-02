plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

surfVelocityApi {
    withCloudClientVelocity()
}

velocityPluginFile {
    main = "dev.slne.surf.settings.velocity.VelocityMain"
    authors = listOf("red")
}

dependencies {
    api(project(":surf-settings-core:surf-settings-core-client"))
}