@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin(module = "jvm")

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin(module = "stdlib-jdk8", version = V.P_KOTLIN))
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = V.JVM
    }
}