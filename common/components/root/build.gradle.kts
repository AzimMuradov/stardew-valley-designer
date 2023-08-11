plugins {
    kotlin("jvm")

    alias(libs.plugins.detekt)
}

dependencies {
    implementation(projects.common.editorEngine)

    implementation(projects.common.components.screenSplash)
    implementation(projects.common.components.screenMainMenu)
    implementation(projects.common.components.screenEditor)
    implementation(projects.common.components.screenSettings)

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
