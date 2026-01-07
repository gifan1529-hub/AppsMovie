    // File: C:/Users/Advan/AndroidStudioProjects/AppsMovie/app/build.gradle.kts

plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.example.appsmovie"

    buildFeatures {
        viewBinding = true
    }

    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appsmovie"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

        kotlinOptions {
            jvmTarget = "1.8"
        }

//    kotlinOptions {
//        jvmTarget = "17"
//    }
}

dependencies {
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.glance.appwidget)
    // Variabel untuk versi yang konsisten
    val room_version = "2.6.1"
    val lifecycle_version = "2.7.0"
    val nav_version = "2.7.7"

//    constraints {
//        implementation("com.squareup:javapoet:1.13.0") {
//            because = "Hilt 2.51.1 needs javapoet 1.13.0. A lower version is being pulled in transitively."
//        }
//    }

    // AndroidX Core & UI
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(libs.material)
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.activity:activity-ktx:1.9.0")

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.androidx.navigation.viewmodel.ktx)
//    implementation("androidx.navigation:navigation-viewmodel-ktx:${nav_version}")

    // Networking - Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")

    // Image Loading - Glide
    implementation("com.github.bumptech.glide:glide:5.0.5")

    // Room Database
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Lifecycle (ViewModel, LiveData, LifecycleScope) - Hapus semua duplikasi
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt Dagger for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    implementation(project(":core"))


    // ViewModel KTX (jika belum ada)
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
//    implementation("androidx.activity:activity-ktx:1.9.0")


    // HAPUS annotationProcessor yang lama karena sudah pakai KSP
    // annotationProcessor("androidx.room:room-compiler:$room_version")
}
