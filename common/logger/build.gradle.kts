plugins {
    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:${V.KOTLIN_LOGGING}")
    implementation("org.slf4j:slf4j-api:${V.SLFJ}")
    implementation("org.apache.logging.log4j:log4j-api:${V.LOG4J}")
    implementation("org.apache.logging.log4j:log4j-core:${V.LOG4J}")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:${V.LOG4J}")

    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:${V.MVI_KOTLIN}")
}

detekt {
    toolVersion = V.DETEKT
    config = files("../config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
