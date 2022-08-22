plugins {
    kotlin("jvm")

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // For XML
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${V.JACKSON}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${V.JACKSON}")
}