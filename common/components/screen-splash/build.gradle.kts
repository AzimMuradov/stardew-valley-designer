plugins {
    kotlin("jvm")

    alias(libs.plugins.detekt)
}

dependencies {
    implementation(libs.bundles.mvikotlin)


    // Meta-code

    detektPlugins(libs.detekt.formatting)

    implementation(projects.common.logger)
    implementation(libs.kotlin.logging.jvm)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from(projectDir.resolve("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}
