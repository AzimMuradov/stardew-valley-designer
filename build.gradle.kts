import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version V.P_KOTLIN apply false

    id("io.gitlab.arturbosch.detekt") version V.P_DETEKT apply false
}

repositories {
    mavenCentral()
}


subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = V.JVM
        }
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
            md.required.set(true)
        }
        ignoreFailures = true
    }
}
