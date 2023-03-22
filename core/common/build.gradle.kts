plugins {
    id("convention.android.library")
}

android {
    namespace = "com.example.android.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}