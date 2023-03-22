
plugins {
    id("convention.android.library")
}

android {
    namespace = "com.example.android.core.datastore"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)

//    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
}
