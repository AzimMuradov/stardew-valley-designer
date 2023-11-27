plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)
                implementation(projects.common.designFormat)

                implementation(projects.common.components.root)
                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                // implementation(projects.common.components.screenSettings)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)


                // Compose Multiplatform

                if (System.getProperty("deploy")?.toBooleanStrictOrNull() == true) {
                    implementation(compose.desktop.linux_x64)
                    implementation(compose.desktop.windows_x64)
                    // implementation(compose.desktop.macos_x64)
                    // implementation(compose.desktop.macos_arm64)
                } else {
                    implementation(compose.desktop.currentOs)
                }
                implementation(compose.materialIconsExtended)

                implementation(projects.common.cmpLibs.buttonsGroup)
                implementation(projects.common.cmpLibs.dropdownMenuModel)
                implementation(projects.common.cmpLibs.dropdownMenu)
                implementation(projects.common.cmpLibs.fileDialogs)
                implementation(projects.common.cmpLibs.sideMenus)

                implementation(projects.common.kmpLibs.browser)
                implementation(projects.common.kmpLibs.clipboard)
                implementation(projects.common.kmpLibs.env)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)

                implementation(libs.directories)
                implementation(libs.kfswatch)

                implementation(libs.kotlinx.datetime)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.stardewvalleydesigner.MainKt"

        System.getProperty("debug")?.let { value ->
            jvmArgs += "-Ddebug=$value"
        }

        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            // obfuscate.set(true)
        }

        // Native distributions are done via `conveyor`, see "conveyor.conf"
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.clean {
    delete(rootDir.resolve("output"))
}
