plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    withCloudClientPaper()
    mainClass("dev.slne.surf.settings.paper.PaperMain")
    bootstrapper("dev.slne.surf.settings.paper.PaperBootstrap")
}

dependencies {
    api(project(":surf-settings-core:surf-settings-core-common"))
    api(project(":surf-settings-api:surf-settings-api-common"))
}