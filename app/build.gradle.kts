
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.portfolio"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.portfolio"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding= true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.52")

    // For @HiltViewModel
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.google.android.material:material:1.11.0")
    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
//    testImplementation(libs.junit.jupiter.api)
//    testRuntimeOnly(libs.junit.jupiter.engine)
//// Robolectric for unit tests with Android views
//    testImplementation(libs.robolectric)
//// AndroidX core for tests
//    testImplementation(libs.androidx.core)
//    testImplementation(libs.androidx.runner)
//    testImplementation(libs.androidx.rules)
//// Optional: for using androidx.test.ext.junit
//    testImplementation(libs.androidx.junit.v116)
//    testImplementation(libs.androidx.junit.v130)
//
//    // For instrumentation tests
//    androidTestImplementation(libs.androidx.junit.v130)
//    testImplementation(libs.kotlinx.coroutines.test)
//    testImplementation(libs.mockk)
//    testImplementation(libs.androidx.core.testing)
//// for InstantTaskExecutorRule
//    testImplementation(libs.junit)
//// For mocking
//    testImplementation(libs.mockk.v1138)
//// For coroutines testing
//    testImplementation(libs.kotlinx.coroutines.test.v173)
// JUnit
// For mocking
// For coroutines testing
// JUnit

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.core.v150)
    testImplementation(libs.androidx.junit.v115)
    testImplementation(libs.mockk.v1137)
    testImplementation(libs.kotlinx.coroutines.test.v171)
    testImplementation(libs.kotlin.test.junit)



}