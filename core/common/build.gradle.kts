plugins {
    id("convention.android.library")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.android.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    api(libs.decompose)
    api(libs.decompose.compose.android)
}