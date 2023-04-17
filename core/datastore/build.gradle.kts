
plugins {
    id("convention.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.android.core.datastore"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)

//    implementation(libs.androidx.dataStore.core)
//    implementation(libs.androidx.dataStore.preferences)
    api(libs.multiplatformSettings)
    api(libs.multiplatformSettings.noArg)
    api(libs.multiplatformSettings.coroutines)
    implementation(libs.kotlinx.serialization.json)
}
