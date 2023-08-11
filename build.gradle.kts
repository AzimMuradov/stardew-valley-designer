import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


plugins {
    kotlin("jvm") version libs.versions.plugin.kotlin.get() apply false

    alias(libs.plugins.detekt) apply false
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

    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required = true
            html.required = true
            txt.required = true
            sarif.required = true
            md.required = true
        }
        ignoreFailures = true
    }
}
