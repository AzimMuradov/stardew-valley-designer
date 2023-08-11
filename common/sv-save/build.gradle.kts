plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version libs.versions.plugin.kotlinx.serialization.get()

    alias(libs.plugins.detekt)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)

    implementation(libs.bundles.xmlutil)


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
