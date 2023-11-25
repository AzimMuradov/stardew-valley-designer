plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()
    js {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.material)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
