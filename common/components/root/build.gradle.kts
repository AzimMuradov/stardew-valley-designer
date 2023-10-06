plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)

                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                // implementation(projects.common.components.screenSettings)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
