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

                implementation(projects.common.kmpLibs.env)

                implementation(projects.common.components.root)
                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.cmpLibs.fileDialogs)
                implementation(projects.common.cmpLibs.tooltip)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.editorMenus.savePlan)
                implementation(projects.common.uiComponents.editorMenus.savePlanAs)
                implementation(projects.common.uiComponents.editorMenus.savePlanImg)
                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)
                implementation(projects.common.uiComponents.windowSizeProvider)

                if (System.getProperty("deploy")?.toBooleanStrictOrNull() == true) {
                    implementation(compose.desktop.linux_x64)
                    implementation(compose.desktop.windows_x64)
                    // implementation(compose.desktop.macos_x64)
                    // implementation(compose.desktop.macos_arm64)
                } else {
                    implementation(compose.desktop.currentOs)
                }
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)

                implementation(libs.directories)
                implementation(libs.kfswatch)


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
