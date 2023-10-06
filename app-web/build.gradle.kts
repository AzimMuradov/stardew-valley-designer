plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig(
                Action {
                    outputFileName = "svd.js"
                }
            )
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)

                implementation(projects.common.designFormat)

                // implementation(projects.common.components.root)
                // implementation(projects.common.components.screenMainMenu)
                // implementation(projects.common.components.screenEditor)
                // implementation(projects.common.components.screenSettings)

                implementation(projects.common.uiUtils.buttonsGroupUi)
                implementation(projects.common.uiUtils.dropdownMenu)
                implementation(projects.common.uiUtils.dropdownMenuUi)
                implementation(projects.common.uiUtils.fileDialogs)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.bundles.mvikotlin)

                implementation(libs.kotlinx.coroutines.core)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}

compose.experimental.web {
    application {}
}

tasks.clean {
    delete(rootProject.buildDir)
    delete(rootDir.resolve("output"))
}
