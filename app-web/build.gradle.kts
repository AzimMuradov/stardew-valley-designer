import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "svd.js"
                export = false
            }

            runTask {
                System.getProperty("debug")?.let { value ->
                    args.plusAssign(listOf("--env", "debug=$value"))
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)
                implementation(projects.common.designFormat)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)


                // Compose Multiplatform

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(projects.common.cmpLibs.buttonsGroup)
                implementation(projects.common.cmpLibs.dropdownMenuModel)
                implementation(projects.common.cmpLibs.dropdownMenu)
                implementation(projects.common.cmpLibs.fileDialogs)
                implementation(projects.common.cmpLibs.sideMenus)

                implementation(projects.common.kmpLibs.browser)
                implementation(projects.common.kmpLibs.clipboard)
                implementation(projects.common.kmpLibs.env)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)

                // implementation(libs.kfswatch)

                implementation(libs.kotlinx.datetime)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}

compose.experimental.web {
    application {}
}
