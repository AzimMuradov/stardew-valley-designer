import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
    alias(libs.plugins.conveyor)
    alias(libs.plugins.detekt)
}

object App {

    const val GROUP: String = "io.stardewvalleydesigner"

    const val NAME: String = "stardew-valley-designer"

    const val VERSION: String = "0.8.2"

    const val DESCRIPTION: String =
        "The goal of this project is to provide a finely tuned editor for designing your farm and the interior of all its buildings."

    const val COPYRIGHT: String = "Copyright (c) 2021-2023 Azim Muradov."
}

group = App.GROUP
version = App.VERSION

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

    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.composejb)

    implementation(libs.bundles.mvikotlin)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.directories)

    implementation(libs.bundles.bonsai)

    implementation(libs.richeditor.compose)


    // Conveyor

    linuxAmd64(compose.desktop.linux_x64)
    windowsAmd64(compose.desktop.windows_x64)
    // macAmd64(compose.desktop.macos_x64)
    // macAarch64(compose.desktop.macos_arm64)


    // Meta-code

    detektPlugins(libs.detekt.formatting)

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

        nativeDistributions {
            packageName = App.NAME
            packageVersion = App.VERSION
            description = App.DESCRIPTION
            copyright = App.COPYRIGHT
            licenseFile = rootProject.file("LICENSE")

            outputBaseDir = rootProject.buildDir.resolve(relative = "bin")

            targetFormats(
                TargetFormat.Deb,
                // TargetFormat.Rpm,
                TargetFormat.Exe,
                // TargetFormat.Pkg, // TODO : Signing
            )
        }
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.clean {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from(projectDir.resolve("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion
