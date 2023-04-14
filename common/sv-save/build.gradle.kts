plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")

    kotlin("plugin.serialization") version V.P_KOTLINX_SERIALIZATION
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common.svcEngine)

    implementation(projects.common.entitiesMetadata)


    implementation(kotlin("stdlib-jdk8"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    // For XML
    implementation("io.github.pdvrieze.xmlutil:core-jvm:${V.XML_UTIL}")
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:${V.XML_UTIL}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
