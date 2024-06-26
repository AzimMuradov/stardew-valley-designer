import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common.kmpLibs.dispatcher)
                implementation(projects.common.kmpLibs.fs)

                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.animation)
                implementation(compose.ui)
                implementation(compose.material)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
        jvmMain {
            dependencies {
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
            }
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(npm(name = "file-saver", version = "2.0.5"))
            }
        }
    }
}
