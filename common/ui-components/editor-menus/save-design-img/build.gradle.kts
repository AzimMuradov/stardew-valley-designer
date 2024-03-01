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

                implementation(projects.common.kmpLibs.dispatcher)
                implementation(projects.common.kmpLibs.fs)
                implementation(projects.common.kmpLibs.png)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.cmpLibs.fileDialogs)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.settingsProvider)

                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
