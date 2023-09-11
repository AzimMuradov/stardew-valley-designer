plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.uiUtils.dropdownMenu)

    implementation(libs.bundles.mvikotlin)


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}
