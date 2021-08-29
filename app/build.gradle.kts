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


    implementation(projects.engine)


    // testImplementation(kotlin(module = "test", version = V.P_KOTLIN))
}


// tasks.test {
//     useJUnitPlatform()
// }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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