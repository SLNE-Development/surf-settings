plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    api(project(":surf-settings-core"))

    runtimeOnly(project(":surf-settings-backend"))
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.settings.paper.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)

    authors.add("red")
}