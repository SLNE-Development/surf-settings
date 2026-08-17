plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.settings.paper.PaperMain")
    generateLibraryLoader(false)
    foliaSupported(true)

    withCorePaper()

    authors.add("red")
}

dependencies {
    api(projects.surfSettingsCore.surfSettingsCoreClient)
}
