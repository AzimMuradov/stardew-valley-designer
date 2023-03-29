plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common.svcEngine)

    implementation(projects.common.uiUtils.dropdownMenu)


    implementation(kotlin("stdlib-jdk8"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${V.DETEKT}")

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")

    // implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")
    // implementation("org.slf4j:slf4j-log4j12:1.7.32")
    // implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.0")
    // implementation("org.apache.logging.log4j:log4j-api:2.17.0")
    // implementation("org.apache.logging.log4j:log4j-core:2.17.0")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../../../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
