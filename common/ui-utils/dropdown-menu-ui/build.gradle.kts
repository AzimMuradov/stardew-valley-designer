import org.jetbrains.compose.compose


plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version V.P_COMPOSE
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(projects.common.uiUtils.dropdownMenu)


    implementation(kotlin("stdlib-jdk8"))

    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.animation)
    implementation(compose.ui)
    implementation(compose.material)
}