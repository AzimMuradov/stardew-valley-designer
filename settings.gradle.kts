pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":app")
include(":sv-api")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")