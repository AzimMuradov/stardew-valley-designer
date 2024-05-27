import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
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

            webpackTask {
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
                implementation(projects.common.entitiesData)
                implementation(projects.common.designFormat)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.dialogNewDesign)
                implementation(projects.common.components.dialogOpenDesign)
                implementation(projects.common.components.dialogOpenSvSave)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.editorMenus.help)
                implementation(projects.common.uiComponents.editorMenus.newDesign)
                implementation(projects.common.uiComponents.editorMenus.openDesign)
                implementation(projects.common.uiComponents.editorMenus.openSvSave)
                implementation(projects.common.uiComponents.editorMenus.saveDesignAs)
                implementation(projects.common.uiComponents.editorMenus.saveDesignImg)
                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)
                implementation(projects.common.uiComponents.windowSizeProvider)

                implementation(compose.runtime)
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


compose.web {}
