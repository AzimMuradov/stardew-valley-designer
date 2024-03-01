import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesData)
                implementation(projects.common.designFormat)

                implementation(projects.common.kmpLibs.clipboard)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.cmpLibs.buttonsGroup)
                implementation(projects.common.cmpLibs.dropdownMenuModel)
                implementation(projects.common.cmpLibs.dropdownMenu)
                implementation(projects.common.cmpLibs.sideMenus)
                implementation(projects.common.cmpLibs.tooltip)

                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)
                implementation(projects.common.uiComponents.windowSizeProvider)

                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
