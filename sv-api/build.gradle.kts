@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version V.P_KOTLIN

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin(module = "stdlib-jdk8", version = V.P_KOTLIN))


    // For XML

    implementation(kotlin(module = "reflect", version = V.P_KOTLIN))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${V.JACKSON}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${V.JACKSON}")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        languageVersion = "1.6"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        jvmTarget = V.JVM
    }
}