plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common.svcEngine)

    implementation(projects.common.components.screenWelcome)
    implementation(projects.common.components.screenMainMenu)
    implementation(projects.common.components.screenCartographer)
    implementation(projects.common.components.screenSettings)

    implementation(projects.common.uiUtils.dropdownMenu)


    implementation(kotlin("stdlib-jdk8"))

    implementation("com.arkivanov.decompose:decompose:${V.DECOMPOSE}")
    implementation("com.arkivanov.mvikotlin:mvikotlin:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:${V.MVI_KOTLIN}")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${V.MVI_KOTLIN}")
}