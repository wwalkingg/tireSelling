plugins {
    id("convention.android.library")
    id("convention.android.library.compose")
    id("kotlinx-serialization")
}

android {
    lint {
        checkDependencies = true
    }
    namespace = "com.example.android.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    api(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.logback.classic)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
}