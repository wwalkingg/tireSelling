
plugins {
    id("convention.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.android.core.model"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}
