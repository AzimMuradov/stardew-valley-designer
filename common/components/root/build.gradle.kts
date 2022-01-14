@file:Suppress("SuspiciousCollectionReassignment")

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

    implementation(compose.foundation)
    implementation(compose.runtime)

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")


    implementation(projects.common.svcEngine)
    implementation(projects.common.svc)
    implementation(projects.common.uiUtils.dropdownMenu)
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = V.JVM
    }
}