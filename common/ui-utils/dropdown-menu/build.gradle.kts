plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}
