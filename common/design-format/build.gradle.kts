plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(projects.common.editorEngine)
    implementation(projects.common.entitiesMetadata)

    implementation(libs.kotlinx.serialization.json)


    // Test

    // testImplementation("io.kotest:kotest-runner-junit5:5.7.2")


    // Meta-code

    implementation(projects.common.logger)
    implementation(libs.kotlinlogging.jvm)
}

// tasks.withType<Test>().configureEach {
//     useJUnitPlatform()
// }
