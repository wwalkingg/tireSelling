plugins {
    id("convention.android.library")
}

android {
    namespace = "com.example.android.core.common"
}

dependencies {
    api(libs.decompose)
    api(libs.decompose.compose.android)
    api(libs.kotlinx.coroutines.android)
}