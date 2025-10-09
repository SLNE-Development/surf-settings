plugins {
    id("dev.slne.surf.surfapi.gradle.core")
}

surfCoreApi {
    withCloudServer()
}

dependencies {
    api(project(":surf-settings-core:surf-settings-core-common"))
    api(project(":surf-settings-api:surf-settings-api-common"))
}