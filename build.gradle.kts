import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "fi.fabianadrian"
version = "1.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    implementation("org.spongepowered:configurate-yaml:4.1.2") {
        exclude("org.yaml")
    }

    implementation("org.incendo:cloud-paper:2.0.0-beta.2")
    implementation("org.incendo:cloud-translations-bukkit:1.0.0-beta.2")

    compileOnly("net.luckperms:api:5.4")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
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
    apiVersion = "1.19"
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