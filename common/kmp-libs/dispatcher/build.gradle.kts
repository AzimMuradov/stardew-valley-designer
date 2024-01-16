import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}
