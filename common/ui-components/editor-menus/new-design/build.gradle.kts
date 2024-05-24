import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.designFormat)

                implementation(projects.common.kmpLibs.dispatcher)

                implementation(projects.common.components.dialogNewDesign)
                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.designDialogs)
                implementation(projects.common.uiComponents.settingsProvider)

                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
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
