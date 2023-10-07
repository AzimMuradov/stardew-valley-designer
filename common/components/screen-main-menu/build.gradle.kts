plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)
                implementation(projects.common.designFormat)
                implementation(projects.common.svSave)

                implementation(libs.kotlinx.coroutines.swing)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
