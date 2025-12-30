    // File: C:/Users/Advan/AndroidStudioProjects/AppsMovie/app/build.gradle.kts

plugins {
    alias(libs.plugins.android.application)
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

    // --- PERBAIKAN UTAMA DI SINI ---
    // Gunakan sintaks yang benar dan versi yang stabil
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appsmovie"
        minSdk = 30
        targetSdk = 34 // Samakan dengan compileSdk
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.glance.appwidget)
    // Variabel untuk versi yang konsisten
    val room_version = "2.6.1"
    val lifecycle_version = "2.7.0"
    val nav_version = "2.7.7"

    // AndroidX Core & UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // Mengambil dari libs.versions.toml, pastikan versinya benar
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("androidx.activity:activity-ktx:1.9.0") // Upgrade ke versi stabil terbaru


    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Networking - Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Image Loading - Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Room Database
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version") // Gunakan KSP untuk Kotlin
    implementation("androidx.room:room-ktx:$room_version")

    // Lifecycle (ViewModel, LiveData, LifecycleScope) - Hapus semua duplikasi
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // HAPUS annotationProcessor yang lama karena sudah pakai KSP
    // annotationProcessor("androidx.room:room-compiler:$room_version")
}
