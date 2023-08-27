import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.conveyor)
    alias(libs.plugins.detekt)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
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

                // implementation(libs.richeditor.compose)


                // Conveyor

                // linuxAmd64(compose.desktop.linux_x64)
                // windowsAmd64(compose.desktop.windows_x64)
                // macAmd64(compose.desktop.macos_x64)
                // macAarch64(compose.desktop.macos_arm64)


                // Meta-code

                // detektPlugins(libs.detekt.formatting)

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.jvm)
            }
        }
    }
}


object App {

    const val GROUP: String = "io.stardewvalleydesigner"

    const val NAME: String = "stardew-valley-designer"

    const val VERSION: String = "0.9.0"

    const val DESCRIPTION: String =
        "The goal of this project is to provide a finely tuned editor for designing your farm and the interior of all its buildings."

    const val COPYRIGHT: String = "Copyright (c) 2021-2023 Azim Muradov."


    const val AUTHOR_URL: String = "https://github.com/AzimMuradov"

    const val REPOSITORY_URL: String = "https://github.com/AzimMuradov/stardew-valley-designer"

    const val CHANGELOG_URL: String = "https://github.com/AzimMuradov/stardew-valley-designer/releases"

    const val BUG_TRACKER_URL: String = "https://github.com/AzimMuradov/stardew-valley-designer/issues"
}

buildConfig {
    className = "App"

    buildConfigField(type = "String", name = "VERSION", value = "\"${App.VERSION}\"")
    buildConfigField(type = "String", name = "COPYRIGHT", value = "\"${App.COPYRIGHT}\"")

    buildConfigField(type = "String", name = "AUTHOR_URL", value = "\"${App.AUTHOR_URL}\"")
    buildConfigField(type = "String", name = "REPOSITORY_URL", value = "\"${App.REPOSITORY_URL}\"")
    buildConfigField(type = "String", name = "CHANGELOG_URL", value = "\"${App.CHANGELOG_URL}\"")
    buildConfigField(type = "String", name = "BUG_TRACKER_URL", value = "\"${App.BUG_TRACKER_URL}\"")

    buildConfigField(type = "long", name = "BUILD_TIME", value = "${System.currentTimeMillis()}L")
}

group = App.GROUP
version = App.VERSION




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

// kotlin {
//     jvmToolchain(17)
// }

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
