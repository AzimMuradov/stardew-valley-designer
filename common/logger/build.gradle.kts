plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinlogging.jvm)
    runtimeOnly(libs.log4j.slf4j2)
    compileOnly(libs.log4j.core)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.logging)
}
