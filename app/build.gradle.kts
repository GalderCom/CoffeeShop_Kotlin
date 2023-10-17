plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //kotlin("plugin.serialization") version "1.6.0"

    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
}

android {
    namespace = "com.example.coffeeshop_20"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.coffeeshop_20"
        minSdk = 28
        targetSdk = 33
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
}

dependencies {

    implementation(platform("io.github.jan-tennert.supabase:bom:1.1.0"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt:0.7.6")
    implementation ("io.ktor:ktor-client-cio:2.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("io.github.jan-tennert.supabase:storage-kt:0.7.6")




    implementation("androidx.core:core-splashscreen:1.0.1")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}