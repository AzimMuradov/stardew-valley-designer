rootProject.name = "stardew-valley-designer"


// Applications

include(":app-desktop")
include(":app-web")


// Editor Engine

include(":common:editor-engine")

// Design Format

include(":common:design-format")

// Entities metadata

include(":common:entities-metadata")

// Save data parser

include(":common:sv-save")


// Components

include(":common:components:root")

include(
    ":common:components:screen-main-menu",
    ":common:components:screen-editor",
    // ":common:components:screen-settings",
)


// Compose UI Utils

include(":common:ui-utils:buttons-group-ui")
include(
    ":common:ui-utils:dropdown-menu",
    ":common:ui-utils:dropdown-menu-ui",
)
include(":common:ui-utils:file-dialogs")


// Logger

include(":common:logger")


// Plugin Management

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}


// Enable typesafe accessors

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
