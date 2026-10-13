import dev.slne.surf.api.gradle.util.slneReleases
import dev.slne.surf.microservice.gradle.plugin.rabbit.RabbitModule

plugins {
    id("dev.slne.surf.api.gradle.minestom")
    id("dev.slne.surf.microservice")
}

surfMinestomApi {
    withCoreMinestom()
}

repositories {
    mavenLocal()
}

minestomPluginFile {
    main = "dev.slne.surf.settings.minestom.SurfSettingsMinestomPlugin"
    authors = listOf("red")

    pluginDependencies {
        register("surf-rabbitmq-minestom")
    }
}

surfMicroservice {
    withRabbitModule(RabbitModule.CLIENT_API)
}

dependencies {
    api(projects.surfSettingsCore.surfSettingsCoreClient)
}

publishing {
    repositories {
        slneReleases()
    }
}
