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
include(":common:components:settings")


// UI Components

include(":common:ui-components:settings-provider")
include(":common:ui-components:themes")


// Custom Kotlin Multiplatform Libraries

include(":common:kmp-libs:browser")
include(":common:kmp-libs:clipboard")
include(":common:kmp-libs:dispatcher")
include(":common:kmp-libs:env")


// Custom Compose Multiplatform Libraries

include(":common:cmp-libs:buttons-group")
include(
    ":common:cmp-libs:dropdown-menu-model",
    ":common:cmp-libs:dropdown-menu",
)
include(":common:cmp-libs:file-dialogs")
include(":common:cmp-libs:side-menus")


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
