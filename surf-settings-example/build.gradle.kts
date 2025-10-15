import dev.slne.surf.surfapi.gradle.util.registerRequired
import net.minecrell.pluginyml.paper.PaperPluginDescription

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

    serverDependencies {
        registerRequired(
            name = "surf-settings-paper",
            loadOrder = PaperPluginDescription.RelativeLoadOrder.BEFORE
        )
    }
}