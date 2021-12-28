@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.compose.compose
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
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:${V.DECOMPOSE}")


    implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")
    implementation("org.slf4j:slf4j-log4j12:1.7.32")
    // implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.0")
    // implementation("org.apache.logging.log4j:log4j-api:2.17.0")
    // implementation("org.apache.logging.log4j:log4j-core:2.17.0")

    implementation(projects.common.svcEngine)
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = V.JVM
    }
}