plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
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
