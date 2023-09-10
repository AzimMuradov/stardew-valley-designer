plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
}

dependencies {
    implementation(libs.kotlinlogging.jvm)
    runtimeOnly(libs.log4j.slf4j2)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.logging)


    // Meta-code

    detektPlugins(libs.detekt.formatting)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from(projectDir.resolve("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}
