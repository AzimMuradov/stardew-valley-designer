plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.designFormat)

                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.dialogNewDesign)
                implementation(projects.common.components.dialogOpenDesign)
                implementation(projects.common.components.dialogOpenSvSave)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
