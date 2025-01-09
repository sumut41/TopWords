plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.googleService)
}

android {
    namespace = "com.skyvo.mobile.core.shared"
    compileSdk = BuildAndroidConfig.compileSdk

    defaultConfig {
        minSdk = BuildAndroidConfig.minSdk
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "IS_DEBUG", "true")
        }
        release {
            buildConfigField("boolean", "IS_DEBUG", "false")
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(Dependencies.kotlinxCoroutines)

    implementation(Dependencies.viewModelScope)
    implementation(Dependencies.lifecycleScope)
    implementation(Dependencies.liveDataKtx)

    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.timber)

    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.firebaseRemoteConfig)
    implementation(Dependencies.firebaseCrashlytics)
}