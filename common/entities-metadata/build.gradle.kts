plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
}

dependencies {
    implementation(projects.common.editorEngine)


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
