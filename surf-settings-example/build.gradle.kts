plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    compileOnly(project(":surf-settings-api:surf-settings-api-common"))
}

surfPaperPluginApi {
    withCloudClientPaper()
    mainClass("dev.slne.surf.settings.example.ExampleMain")
    generateLibraryLoader(false)

    authors.add("red")
}