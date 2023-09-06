plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
    alias(libs.plugins.detekt)
}

dependencies {
    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.animation)
    implementation(compose.ui)
    implementation(compose.material)

    implementation(libs.lwjgl.asProvider())
    implementation(libs.lwjgl.tinyfd)
    setOf("linux", "windows").forEach { platform ->
        runtimeOnly("${libs.lwjgl.asProvider().get()}:natives-$platform")
        runtimeOnly("${libs.lwjgl.tinyfd.get()}:natives-$platform")
    }


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
