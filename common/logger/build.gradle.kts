plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinlogging.common)

                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.logging)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.log4j.slf4j2)
            }
        }
    }
}
