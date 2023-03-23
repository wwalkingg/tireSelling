plugins {
    id("convention.android.library")
}

android {
    namespace = "com.example.android.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    api(libs.decompose)
    api(libs.decompose.compose.android)
}