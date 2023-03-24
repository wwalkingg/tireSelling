plugins {
    id("convention.android.library")
    id("convention.android.library.compose")
}

android {
    namespace = "com.example.android.core.ui"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)

}