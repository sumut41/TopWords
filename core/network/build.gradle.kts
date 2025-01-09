plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.parcelize)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "com.skyvo.mobile.core.network"
    compileSdk = BuildAndroidConfig.compileSdk

    defaultConfig {
        minSdk = BuildAndroidConfig.minSdk
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
        jvmTarget = BuildAndroidConfig.jvmTarget
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        buildConfig = true
    }
}


dependencies {
    implementation(project(BuildModules.CORE.SHARED))
    implementation(Dependencies.androidXCore)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.googleMaterial)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesAndroid)

    implementation(Dependencies.securityCrypto)
    implementation(Dependencies.multidex)
    implementation(Dependencies.timber)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofitCoroutinesAdapter)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpInterceptor)
    implementation(Dependencies.okhttpUrlConnection)
    implementation(Dependencies.retrofitMoshi)
    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodegen)

    debugImplementation(Dependencies.chuckerDebug) {
        exclude(group = "com.google.android.material", module = "material")
        exclude(group = "androidx.constraintlayout", module = "constraintlayout")
    }
    releaseImplementation(Dependencies.chuckerRelease)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
}