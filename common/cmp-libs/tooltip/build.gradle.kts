import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
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
                // TODO : Should be removed (cmp-lib <-/-- ui-component); should be solved after moving tp M3
                implementation(projects.common.uiComponents.themes)

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
