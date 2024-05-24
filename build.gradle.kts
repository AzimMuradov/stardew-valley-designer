import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose) apply false
}

subprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }


    // Kotlin configurations

    tasks {
        withType<KotlinCompilationTask<*>>().configureEach {
            compilerOptions {
                // languageVersion = KotlinVersion.KOTLIN_2_1
                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }

        withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_17
            }
        }
    }
}
