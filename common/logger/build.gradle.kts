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
                implementation(projects.common.kmpLibs.env)

                implementation(libs.kotlinlogging.common)

                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.logging)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.kotlinlogging.jvm)
                runtimeOnly(libs.log4j.slf4j2)
                compileOnly(libs.log4j.core)
            }
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.kotlinlogging.wasmJs)
            }
        }
    }
}
