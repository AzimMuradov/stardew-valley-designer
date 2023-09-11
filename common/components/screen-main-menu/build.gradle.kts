plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)
    implementation(projects.common.svSave)

    implementation(libs.bundles.mvikotlin)

    implementation(libs.kotlinx.coroutines.core)


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}
