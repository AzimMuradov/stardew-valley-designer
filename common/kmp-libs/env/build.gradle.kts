plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        jvmMain {
            dependencies {
                implementation(libs.directories)
            }
        }
    }
}
