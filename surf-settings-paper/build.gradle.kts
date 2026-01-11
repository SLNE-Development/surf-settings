plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    api(project(":surf-settings-core"))
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.settings.paper.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)
    
    authors.add("red")
}