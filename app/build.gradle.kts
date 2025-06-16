plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.musicapp_103"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.musicapp_103"
        minSdk = 26
        targetSdk = 35
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
    buildFeatures {
        compose = true
    }

}

dependencies {
    // Thư viện chính
    implementation("androidx.compose.runtime:runtime-livedata:1.3.1")  // Phiên bản cập nhật
    implementation("androidx.compose.runtime:runtime:1.3.1")  // Phiên bản cập nhật
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.0")

    // Firebase và Firestore
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    // Thư viện tải ảnh
    implementation("io.coil-kt:coil-compose:2.4.0") // Sử dụng phiên bản mới nhất

    // Accompanist Navigation Animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.3-beta")

    // Các thư viện AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Thư viện kiểm tra
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Thư viện debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
