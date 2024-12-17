plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //DaggerHilt
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.alebrije_estudios.metabolique"
    compileSdk = 35

    //Configure Languages
    androidResources{
        generateLocaleConfig = true
    }

    defaultConfig {
        applicationId = "com.alebrije_estudios.metabolique"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        //Configure Languages
        resourceConfigurations.plus(listOf("en_US","es"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        //Configure Date
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //implementation(libs.androidx.material)
    //IconsLibrary
    implementation(libs.androidx.material.icons.extended)
    //Livedata
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.runtime.livedata)
    //Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    //DaggerHilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.places)
    kapt(libs.hilt.android.compiler)
    //Navigation
    implementation(libs.androidx.navigation.compose)
    //Desugaring for date
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    //SplashScreen
    implementation(libs.androidx.core.splashscreen)
    //Using in carousel card slider
    implementation(libs.androidx.ui.util)
    //download images with coil
    implementation(libs.coil.compose)
    //graphics library
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
    implementation(libs.vico.views)

    //permission
    implementation(libs.accompanist.permissions)
    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}