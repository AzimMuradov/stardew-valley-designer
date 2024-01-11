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
                implementation(projects.common.editorEngine)

                implementation(projects.common.components.screenEditor)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
