plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common.svcEngine)

    implementation(projects.common.entitiesMetadata)

    implementation(projects.common.svSave)

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
