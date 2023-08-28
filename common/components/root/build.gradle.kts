plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)

                implementation(projects.common.components.screenSplash)
                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.screenSettings)

                implementation(libs.decompose)
                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
