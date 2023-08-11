plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version V.P_KOTLINX_SERIALIZATION

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)

    implementation("io.github.pdvrieze.xmlutil:core-jvm:${V.XML_UTIL}")
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:${V.XML_UTIL}")


    // Meta-code

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation(projects.common.logger)
    implementation("io.github.microutils:kotlin-logging-jvm:${V.KOTLIN_LOGGING}")
}

detekt {
    toolVersion = V.DETEKT
    config.from("../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
