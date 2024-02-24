import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl


plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "svd.js"
                export = false
            }

            runTask {
                System.getProperty("debug")?.let { value ->
                    args.plusAssign(listOf("--env", "debug=$value"))
                }
            }

            webpackTask {
                System.getProperty("debug")?.let { value ->
                    args.plusAssign(listOf("--env", "debug=$value"))
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(projects.common.editorEngine)
                implementation(projects.common.entitiesMetadata)
                implementation(projects.common.designFormat)

                implementation(projects.common.components.screenEditor)
                implementation(projects.common.components.settings)

                implementation(projects.common.uiComponents.editor)
                implementation(projects.common.uiComponents.editorMenus.saveDesignAs)
                implementation(projects.common.uiComponents.editorMenus.saveDesignImg)
                implementation(projects.common.uiComponents.settingsProvider)
                implementation(projects.common.uiComponents.themes)
                implementation(projects.common.uiComponents.windowSizeProvider)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.mvikotlin)


                // Meta-code

                implementation(projects.common.logger)
                implementation(libs.kotlinlogging.common)
            }
        }
    }
}


// Resource management

val copyWebResources by tasks.registering(Copy::class) {
    from(
        "../common/ui-components/editor/build/processedResources/wasmJs/main",
        "../common/ui-components/themes/build/processedResources/wasmJs/main",
    )
    into("build/processedResources/wasmJs/main")

    dependsOn(":app-web:wasmJsMainClasses")
}

tasks.named("compileDevelopmentExecutableKotlinWasmJs").configure {
    dependsOn(copyWebResources)
}

tasks.named("compileProductionExecutableKotlinWasmJs").configure {
    dependsOn(copyWebResources)
}

tasks.named("wasmJsJar").configure {
    dependsOn(copyWebResources)
}


compose.experimental.web {
    application {}
}
