@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm")
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
    implementation(kotlin(module = "stdlib-jdk8", version = V.P_KOTLIN))

    implementation(compose.desktop.currentOs)

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:${V.DECOMPOSE}")


    implementation(projects.common.svcEngine)
    implementation(projects.common.svc)
    implementation(projects.common.components.root)
    implementation(projects.common.uiUtils.buttonsGroup)
    implementation(projects.common.uiUtils.dropdownMenu)
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        languageVersion = "1.6"
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = V.JVM
    }
}

compose.desktop {
    application {
        mainClass = "me.azimmuradov.svc.MainKt"
        // jvmArgs += listOf("-Xmx2G")
        // args += listOf("-customArgument")

        nativeDistributions {
            packageName = SVC.NAME
            packageVersion = SVC.VERSION
            description = SVC.DESCRIPTION
            copyright = SVC.COPYRIGHT
            // vendor = ""

            targetFormats(
                TargetFormat.Deb, TargetFormat.Rpm,
                TargetFormat.Exe, TargetFormat.Msi,
                /*TargetFormat.Dmg, TargetFormat.Pkg,*/
            )
        }
    }
}