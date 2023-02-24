plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")

    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    // For XML
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${V.JACKSON}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${V.JACKSON}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
