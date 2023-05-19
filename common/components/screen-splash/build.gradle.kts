plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")


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
