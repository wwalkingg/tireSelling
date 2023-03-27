plugins {
    id("convention.android.feature")
    id("convention.android.library.compose")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.android.feature.home"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":feature:my-subscribe"))
    implementation("com.github.madrapps:plot:0.1.1")
}