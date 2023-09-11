plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)

    implementation(libs.bundles.xmlutil)


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}
