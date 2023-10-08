plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)

                implementation(projects.common.designFormat)

                implementation(projects.common.components.root)
                implementation(projects.common.components.screenMainMenu)
                implementation(projects.common.components.screenEditor)
                // implementation(projects.common.components.screenSettings)

                implementation(projects.common.uiLibs.dropdownMenu)
                implementation(projects.common.uiLibs.dropdownMenuUi)
                implementation(projects.common.uiLibs.buttonsGroupUi)
                implementation(projects.common.uiLibs.fileDialogsUi)

                if (System.getProperty("deploy")?.toBooleanStrictOrNull() == true) {
                    implementation(compose.desktop.linux_x64)
                    implementation(compose.desktop.windows_x64)
                    // implementation(compose.desktop.macos_x64)
                    // implementation(compose.desktop.macos_arm64)
                } else {
                    implementation(compose.desktop.currentOs)
                }

                implementation(compose.materialIconsExtended)

                implementation(libs.bundles.mvikotlin)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.directories)

                implementation("io.github.irgaly.kfswatch:kfswatch:1.0.0")


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.jvm)
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
    delete(rootProject.buildDir)
    delete(rootDir.resolve("output"))
}
