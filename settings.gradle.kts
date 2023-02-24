rootProject.name = "stardew-valley-cartographer"


// Applications

include(":app-desktop")
// TODO : include(":app-web")

// SVC Engine

include(":common:svc-engine")

// Save data parser

include(":common:sv-save")

// Components

include(":common:components:root")

include(
    ":common:components:screen-welcome",
    ":common:components:screen-main-menu",
    ":common:components:screen-cartographer",
    ":common:components:screen-settings",
)

// Compose UI Utils

include(":common:ui-utils:buttons-group-ui")
include(
    ":common:ui-utils:dropdown-menu",
    ":common:ui-utils:dropdown-menu-ui",
)


// Plugin Management

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// Enable typesafe accessors

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
