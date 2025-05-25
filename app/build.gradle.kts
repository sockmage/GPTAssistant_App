plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.gptassistant"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gptassistant"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        buildConfigField("String", "OPENAI_API_KEY", "\"${project.findProperty("OPENAI_API_KEY") ?: ""}\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
        dataBinding = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    
    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v290)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    
    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    
    // Markdown support
    implementation(libs.core)
    implementation(libs.ext.strikethrough)
    implementation(libs.ext.tables)
    implementation(libs.ext.tasklist)
    implementation(libs.html)
    
    // Image loading
    implementation(libs.coil.compose)
    
    // Audio/Video processing
    implementation(":mobile-ffmpeg-full-gpl-4.4.LTS@aar")
    
    // Room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin"
            && requested.name != "kotlin-metadata-jvm"
        ) {
            useVersion("1.9.22")
            because("Force all Kotlin dependencies to 1.9.22 to avoid 2.x conflicts, except kotlin-metadata-jvm")
        }
    }
}