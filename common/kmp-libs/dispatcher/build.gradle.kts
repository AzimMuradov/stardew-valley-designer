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
