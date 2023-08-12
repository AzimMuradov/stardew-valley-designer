plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
    alias(libs.plugins.detekt)
}

dependencies {
    implementation(projects.common.uiUtils.dropdownMenu)

    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.animation)
    implementation(compose.ui)
    implementation(compose.material)


    // Meta-code

    detektPlugins(libs.detekt.formatting)

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from(projectDir.resolve("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}
