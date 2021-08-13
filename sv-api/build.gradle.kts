plugins {
    kotlin("jvm") version V.P_KOTLIN

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8", V.P_KOTLIN))

    // For XML
    implementation(kotlin("reflect", V.P_KOTLIN))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${V.JACKSON}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${V.JACKSON}")
}