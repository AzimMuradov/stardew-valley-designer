import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version V.P_COMPOSE

    id("io.gitlab.arturbosch.detekt")
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

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${V.KOTLINX_COROUTINES}")

    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")

    implementation("dev.dirs:directories:${V.DIRECTORIES}")

    implementation("cafe.adriel.bonsai:bonsai-core:${V.BONSAI}")
    implementation("cafe.adriel.bonsai:bonsai-file-system:${V.BONSAI}")

    implementation("com.mohamedrejeb.richeditor:richeditor-compose:${V.RICH_EDITOR}")


    // Meta-code

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation(projects.common.logger)
    implementation("io.github.microutils:kotlin-logging-jvm:${V.KOTLIN_LOGGING}")
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
            licenseFile.set(rootProject.file("LICENSE"))

            outputBaseDir.set(rootProject.buildDir.resolve(relative = "bin"))

            targetFormats(
                TargetFormat.Deb,
                // TargetFormat.Rpm,
                TargetFormat.Exe, TargetFormat.Msi,
                // TargetFormat.Pkg, // TODO : Signing
            )

            linux {
                iconFile.set(sourceSets.main.get().output.resourcesDir!!.resolve("icon.png"))
            }
            windows {
                iconFile.set(sourceSets.main.get().output.resourcesDir!!.resolve("icon.ico"))
            }
        }
    }
}

tasks.clean {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = V.DETEKT
    config = files("../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
