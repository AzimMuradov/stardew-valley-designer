plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)

    implementation(projects.common.components.root)
    implementation(projects.common.components.screenSplash)
    implementation(projects.common.components.screenMainMenu)
    implementation(projects.common.components.screenEditor)
    implementation(projects.common.components.screenSettings)

    implementation(projects.common.uiUtils.buttonsGroupUi)
    implementation(projects.common.uiUtils.dropdownMenu)
    implementation(projects.common.uiUtils.dropdownMenuUi)
    implementation(projects.common.uiUtils.fileDialogs)

    if (System.getProperty("deploy")?.toBooleanStrictOrNull() == true) {
        implementation(compose.desktop.linux_x64)
        implementation(compose.desktop.windows_x64)
        // implementation(compose.desktop.macos_x64)
        // implementation(compose.desktop.macos_arm64)
    } else {
        implementation(compose.desktop.currentOs)
    }

    implementation(compose.materialIconsExtended)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.composejb)

    implementation(libs.bundles.mvikotlin)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.directories)


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
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
