plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.directories)
            }
        }
    }
}
