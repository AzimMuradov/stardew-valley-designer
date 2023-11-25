plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    js {
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
