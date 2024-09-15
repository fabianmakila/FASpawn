import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.1"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.diffplug.spotless") version "7.0.0.BETA2"
}

group = "fi.fabianadrian"
version = "2.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    implementation("org.spongepowered:configurate-yaml:4.1.2") {
        exclude("org.yaml")
    }

    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-beta.10")

    compileOnly("net.luckperms:api:5.4")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()

        sequenceOf(
            "org.incendo.cloud",
            "org.spongepowered.configurate",
            "io.leangen.geantyref"
        ).forEach { pkg ->
            relocate(pkg, "fi.fabianadrian.faspawn.dependency.$pkg")
        }
    }
}

paper {
    main = "fi.fabianadrian.faspawn.FASpawn"
    apiVersion = "1.19.4"
    authors = listOf("FabianAdrian")
    dependencies {
        serverDependencies {
            register("LuckPerms") {
                load = PaperPluginDescription.RelativeLoadOrder.BEFORE
                required = false
            }
        }
    }
}

spotless {
    java {
        endWithNewline()
        formatAnnotations()
        indentWithTabs()
        removeUnusedImports()
        trimTrailingWhitespace()
    }
}