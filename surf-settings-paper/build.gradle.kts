plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    withCloudClientPaper()
    mainClass("dev.slne.surf.settings.paper.PaperMain")
    bootstrapper("dev.slne.surf.settings.paper.PaperBootstrap")

    authors.add("red")
}

dependencies {
    api(project(":surf-settings-core:surf-settings-core-client"))
}