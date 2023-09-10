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

    val platforms = if (System.getProperty("deploy")?.toBooleanStrictOrNull() == true) {
        setOf("linux", "windows"/* , "macos", "macos-arm64" */)
    } else {
        val platform = Pair(
            System.getProperty("os.name")!!,
            System.getProperty("os.arch")!!
        ).let { (name, arch) ->
            when {
                arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any(name::startsWith) -> "linux"

                arrayOf("Windows").any(name::startsWith) -> "windows"

                // arrayOf("Mac OS X", "Darwin").any(name::startsWith) -> {
                //     val archPostfix = if (arch.startsWith("aarch64")) "-arm64" else ""
                //     "macos$archPostfix"
                // }

                else -> error("""Unrecognized or unsupported platform. Please set "platform" manually""")
            }
        }
        setOf(platform)
    }

    platforms.forEach { platform ->
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
