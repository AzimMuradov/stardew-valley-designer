plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesData)
                implementation(projects.common.designFormat)

                implementation(projects.common.kmpLibs.fs)

                implementation(projects.common.components.dialogNewDesign)
                implementation(projects.common.components.dialogOpenDesign)
                implementation(projects.common.components.dialogOpenSvSave)

                implementation(libs.kotlinx.coroutines.swing)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
