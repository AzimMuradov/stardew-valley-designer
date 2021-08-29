pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":app")
include(":engine")
include(":savedata")
include(":renderer")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")