rootProject.name = "stardew-valley-designer"


// Applications

include(":app-desktop")
// TODO : include(":app-web")

// Editor Engine

include(":common:editor-engine")

// Entities metadata

include(":common:entities-metadata")

// Save data parser

include(":common:sv-save")

// Components

include(":common:components:root")

include(
    ":common:components:screen-splash",
    ":common:components:screen-main-menu",
    ":common:components:screen-editor",
    ":common:components:screen-settings",
)

// Compose UI Utils

include(":common:ui-utils:buttons-group-ui")
include(
    ":common:ui-utils:dropdown-menu",
    ":common:ui-utils:dropdown-menu-ui",
)

// Logger

include(":common:logger")


// Plugin Management

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// Enable typesafe accessors

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
