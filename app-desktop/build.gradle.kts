import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")

    id("org.jetbrains.compose") version V.P_COMPOSE
}

group = SVC.GROUP
version = SVC.VERSION

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(projects.common.svcEngine)

    implementation(projects.common.entitiesMetadata)

    implementation(projects.common.components.root)
    implementation(projects.common.components.screenWelcome)
    implementation(projects.common.components.screenMainMenu)
    implementation(projects.common.components.screenCartographer)
    implementation(projects.common.components.screenSettings)

    implementation(projects.common.uiUtils.buttonsGroupUi)
    implementation(projects.common.uiUtils.dropdownMenu)
    implementation(projects.common.uiUtils.dropdownMenuUi)


    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)

    implementation("cafe.adriel.bonsai:bonsai-core:1.2.0")
    implementation("cafe.adriel.bonsai:bonsai-file-system:1.2.0")

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")

    implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")
    implementation("org.slf4j:slf4j-log4j12:2.0.7")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
}


compose.desktop {
    application {
        mainClass = "me.azimmuradov.svc.MainKt"
        // jvmArgs += listOf("-Xmx2G")
        // args += listOf("-customArgument")

        buildTypes {
            release {
                proguard {
                    configurationFiles.from(project.file("compose-desktop.pro"))
                }
            }
        }

        nativeDistributions {
            packageName = SVC.NAME
            packageVersion = SVC.VERSION
            description = SVC.DESCRIPTION
            copyright = SVC.COPYRIGHT
            licenseFile.set(rootProject.file("LICENSE"))

            outputBaseDir.set(rootProject.buildDir.resolve(relative = "bin"))

            targetFormats(
                TargetFormat.Deb,
                // TargetFormat.Rpm,
                TargetFormat.Exe, TargetFormat.Msi,
                // TargetFormat.Pkg, // TODO : Signing
            )
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
