plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.bharath.swipeandroidassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bharath.swipeandroidassignment"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
//    implementation(libs.koin.viewmodel)
    /*
  Life cycle
   */

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    /*
   Room Database
    */
    //noinspection GradleDependency
    implementation(libs.androidx.room.runtime)
    //noinspection GradleDependency
    ksp(libs.androidx.room.compiler)
//    implementation("androidx.room:room-coroutines:2.1.0-alpha04")
    // Kotlin Extensions and Coroutines support for Room
    //noinspection GradleDependency
    implementation(libs.androidx.room.ktx)
    //noinspection GradleDependency


    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.okhttp)

    implementation(libs.androidx.core.splashscreen)
    implementation(kotlin("stdlib"))
    implementation(libs.jetbrains.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)

    implementation(libs.glide)

    implementation(libs.androidx.core.animation)


}