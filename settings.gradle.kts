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


// Custom Compose Multiplatform UI Libraries

include(
    ":common:ui-libs:dropdown-menu",
    ":common:ui-libs:dropdown-menu-ui",
)
include(":common:ui-libs:side-menus-ui")
include(":common:ui-libs:buttons-group-ui")
include(":common:ui-libs:file-dialogs-ui")


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
