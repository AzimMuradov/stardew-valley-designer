plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(projects.common.svcEngine)


    // Meta-code

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation(projects.common.logger)
    implementation("io.github.microutils:kotlin-logging-jvm:${V.KOTLIN_LOGGING}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
