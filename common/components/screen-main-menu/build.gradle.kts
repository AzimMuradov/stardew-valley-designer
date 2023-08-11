plugins {
    kotlin("jvm")

    alias(libs.plugins.detekt)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)
    implementation(projects.common.svSave)

    implementation(libs.bundles.mvikotlin)

    implementation(libs.kotlinx.coroutines.core)


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
