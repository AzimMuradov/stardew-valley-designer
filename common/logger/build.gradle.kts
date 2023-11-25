plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    js {
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
        jsMain {
            dependencies {
                implementation(libs.kotlinlogging.js)
            }
        }
    }
}
