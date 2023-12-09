rootProject.name = "stardew-valley-designer"


// Applications

include(":app-desktop")
include(":app-web")


// Core Modules

include(":common:editor-engine")
include(":common:entities-metadata")
include(":common:design-format")
include(":common:sv-save")


// Components

include(":common:components:root")
include(
    ":common:components:screen-main-menu",
    ":common:components:screen-editor",
    // ":common:components:screen-settings",
)


// Compose UI Utilities

include(":common:ui-utils:buttons-group-ui")
include(
    ":common:ui-utils:dropdown-menu",
    ":common:ui-utils:dropdown-menu-ui",
)
include(":common:ui-utils:file-dialogs")


// Utilities

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
