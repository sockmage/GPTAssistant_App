import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifactory.cronapp.io/libs-snapshot") }
        maven { url = uri("https://jitpack.io") }
        flatDir {
            dirs("app/libs")
        }
    }
}

// Load properties from config directory
val propertiesFile = File(rootDir, "config/gradle.properties")
if (propertiesFile.exists()) {
    val properties = Properties()
    propertiesFile.inputStream().use { properties.load(it) }
    properties.forEach { key, value ->
        extra.set(key as String, value)
    }
}

val localPropertiesFile = File(rootDir, "config/local.properties")
if (localPropertiesFile.exists()) {
    val properties = Properties()
    localPropertiesFile.inputStream().use { properties.load(it) }
    properties.forEach { key, value ->
        extra.set(key as String, value)
    }
}

rootProject.name = "Lingro"
include(":app")
