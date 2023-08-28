import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
}

subprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            languageVersion = KotlinVersion.KOTLIN_1_9
            jvmTarget = JvmTarget.JVM_17
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}
