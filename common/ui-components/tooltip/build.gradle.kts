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
                implementation(projects.common.uiComponents.themes)


                // Compose Multiplatform

                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)

                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }

        jvmMain {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}
