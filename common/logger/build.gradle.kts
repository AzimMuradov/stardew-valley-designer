plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    js(IR) {
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
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlinlogging.jvm)
                runtimeOnly(libs.log4j.slf4j2)
                compileOnly(libs.log4j.core)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlinlogging.js)
            }
        }
    }
}
