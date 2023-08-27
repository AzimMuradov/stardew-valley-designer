plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.detekt)
}

kotlin {
    jvm()
}

// dependencies {
//     implementation(libs.bundles.mvikotlin)
//
//
//     // Meta-code
//
//     detektPlugins(libs.detekt.formatting)
//
//     implementation(projects.common.logger)
//     implementation(libs.kotlinlogging.jvm)
// }

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from(projectDir.resolve("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}
