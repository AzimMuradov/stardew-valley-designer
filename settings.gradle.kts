pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":app-desktop")
// TODO : include(":app-web")

include(":common:svc-engine")
include(":common:svc")
include(":common:components")
include(":common:ui-utils")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")