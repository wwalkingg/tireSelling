plugins {
    id("convention.android.application")
    id("convention.android.application.compose")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    defaultConfig {
        applicationId = "com.example.android"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.example.android"
}


dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:all-articles"))
    implementation(project(":feature:article-detail"))
    implementation(project(":feature:product-detail"))
    implementation(project(":feature:address-management"))
    implementation(project(":feature:collection-product"))
    implementation(project(":feature:modifier-userinfo"))
    implementation(project(":feature:login"))
    implementation(project(":feature:order-management"))
    implementation(project(":feature:reward-points"))
    implementation(project(":feature:store-detail"))
    implementation(project(":feature:coupon-center"))
    implementation(project(":feature:model-detail"))
    implementation(project(":feature:brand-detail"))
    implementation(project(":feature:search-result"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)

    implementation(libs.coil.kt)
    implementation(libs.decompose)
    implementation(libs.decompose.compose.android)
}