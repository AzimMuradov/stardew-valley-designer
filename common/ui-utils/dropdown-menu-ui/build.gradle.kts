plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version V.P_COMPOSE

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(projects.common.uiUtils.dropdownMenu)

    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.animation)
    implementation(compose.ui)
    implementation(compose.material)


    // Meta-code

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation(projects.common.logger)
    implementation("io.github.microutils:kotlin-logging-jvm:${V.KOTLIN_LOGGING}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
