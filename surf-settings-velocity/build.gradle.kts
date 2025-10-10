plugins {
    id("dev.slne.surf.surfapi.gradle.velocity")
}

surfVelocityApi {
    withCloudClientVelocity()

    authors.add("red")
}

dependencies {
    api(project(":surf-settings-core:surf-settings-core-common"))
    api(project(":surf-settings-api:surf-settings-api-common"))
}