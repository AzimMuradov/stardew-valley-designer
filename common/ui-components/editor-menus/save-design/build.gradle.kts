plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesData)
                implementation(projects.common.designFormat)

                implementation(projects.common.kmpLibs.dispatcher)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.settingsProvider)

                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}
