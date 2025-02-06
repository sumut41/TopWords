plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.parcelize)
    id(Plugins.googleService)
}

android {
    namespace = "com.skyvo.mobile.core.base"
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
            isMinifyEnabled = false
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
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeAndroidCompilerVersion
    }
}

dependencies {
    api(project(BuildModules.CORE.SHARED))
    implementation(Dependencies.androidXCore)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.supportConstraint)
    implementation(Dependencies.viewModelScope)
    implementation(Dependencies.lifecycleScope)
    implementation(Dependencies.liveDataKtx)

    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.firebaseRemoteConfig)

    implementation(Dependencies.multidex)
    implementation(Dependencies.timber)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesAndroid)
    implementation(Dependencies.securityCrypto)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.activityKtx)
    implementation(Dependencies.navigationFragment)

    implementation(platform(ComposeLibs.Bom))
    implementation(ComposeLibs.UI)
    implementation(Dependencies.lottie)
}