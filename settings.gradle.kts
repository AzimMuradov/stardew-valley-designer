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
include(
    ":common:components:dialog-new-design",
    ":common:components:dialog-open-design",
    ":common:components:dialog-open-sv-save",
)
include(":common:components:settings")


// UI Components

include(":common:ui-components:editor")
include(
    ":common:ui-components:editor-menus:new-design",
    ":common:ui-components:editor-menus:open-design",
    ":common:ui-components:editor-menus:open-sv-save",
    ":common:ui-components:editor-menus:save-design",
    ":common:ui-components:editor-menus:save-design-as",
    ":common:ui-components:editor-menus:save-design-img",
)
include(":common:ui-components:design-dialogs")
include(":common:ui-components:settings-provider")
include(":common:ui-components:themes")
include(":common:ui-components:window-size-provider")


// Custom Kotlin Multiplatform Libraries

include(":common:kmp-libs:clipboard")
include(":common:kmp-libs:dispatcher")
include(":common:kmp-libs:env")
include(":common:kmp-libs:fs")
include(":common:kmp-libs:png")


// Custom Compose Multiplatform Libraries

include(":common:cmp-libs:buttons-group")
include(
    ":common:cmp-libs:dropdown-menu-model",
    ":common:cmp-libs:dropdown-menu",
)
include(":common:cmp-libs:file-dialogs")
include(":common:cmp-libs:side-menus")
include(":common:cmp-libs:tooltip")


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
