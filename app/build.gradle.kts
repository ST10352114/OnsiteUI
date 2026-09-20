plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.onsite_mockups"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.onsite_mockups"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(
        platform(
            libs.androidx.compose.bom
        )
    )



    implementation(
        libs.androidx.activity.compose
    )

    implementation(
        libs.androidx.compose.material3
    )

    implementation(
        "androidx.compose.material:material-icons-extended"
    )

    implementation(
        libs.androidx.compose.ui
    )

    implementation(
        libs.androidx.compose.ui.graphics
    )

    implementation(
        libs.androidx.compose.ui.tooling.preview
    )

    implementation(
        libs.androidx.core.ktx
    )

    implementation(
        libs.androidx.lifecycle.runtime.ktx
    )

    implementation(
        "androidx.navigation:navigation-compose:2.10.1"
    )

    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )

    implementation(
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
    )

    implementation(
        "com.squareup.okhttp3:okhttp:4.12.0"
    )

    implementation(
        "com.squareup.okhttp3:logging-interceptor:4.12.0"
    )

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7"
    )

    // Serialization & Datetime
    implementation(
        libs.kotlinx.serialization.json
    )

    implementation(
        libs.kotlinx.datetime
    )

    // Supabase
    implementation(
        platform(libs.supabase.bom)
    )

    implementation(
        libs.supabase.gotrue
    )

    implementation(
        libs.supabase.postgrest
    )

    implementation(
        libs.ktor.client.android
    )

    implementation(
        libs.ktor.client.core
    )

    // Firebase Cloud Messaging
    implementation(
        platform(
            "com.google.firebase:firebase-bom:33.7.0"
        )
    )

    implementation(
        "com.google.firebase:firebase-messaging"
    )

    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2"
    )

    testImplementation(
        libs.junit
    )

    androidTestImplementation(
        platform(
            libs.androidx.compose.bom
        )
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}