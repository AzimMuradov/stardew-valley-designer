plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
}

dependencies {
    implementation(projects.common.uiUtils.dropdownMenu)

    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.animation)
    implementation(compose.ui)
    implementation(compose.material)


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}
