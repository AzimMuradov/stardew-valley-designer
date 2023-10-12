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

                implementation(projects.common.cmpLibs.dropdownMenu)
                implementation(projects.common.cmpLibs.dropdownMenuUi)
                implementation(projects.common.cmpLibs.sideMenusUi)
                implementation(projects.common.cmpLibs.buttonsGroupUi)
                implementation(projects.common.cmpLibs.fileDialogsUi)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)


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
