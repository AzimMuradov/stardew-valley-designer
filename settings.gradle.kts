rootProject.name = "stardew-valley-cartographer"


// Applications

include(":app-desktop")
// TODO : include(":app-web")

// SVC Engine

include(":common:svc-engine")


// Components

include(":common:components:root")

include(":common:svc")

// Compose UI Utils

include(":common:ui-utils:buttons-group")
include(":common:ui-utils:dropdown-menu")


// Plugin Management

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// Enable typesafe accessors

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")