plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.googleService)
    id(Plugins.firebaseCrashlytics)
    id(Plugins.hilt)
    id(Plugins.navigationSafeargs)
}

android {
    namespace = BuildAndroidConfig.applicationID
    compileSdk = BuildAndroidConfig.compileSdk

    defaultConfig {
        applicationId = BuildAndroidConfig.applicationID
        minSdk = BuildAndroidConfig.minSdk
        targetSdk = BuildAndroidConfig.targetSdk
        versionCode = BuildAndroidConfig.versionCode
        versionName = BuildAndroidConfig.versionName

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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeAndroidCompilerVersion
    }
}

dependencies {
    implementation(project(BuildModules.CORE.BASE))
    implementation(project(BuildModules.CORE.UIKIT))

    implementation(Dependencies.androidXCore)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.googleMaterial)
    implementation(Dependencies.supportConstraint)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.viewModelScope)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesAndroid)
    implementation(Dependencies.retrofitGson)

    // test
    testImplementation(Dependencies.junitTest)
    testImplementation(Dependencies.mockkTest)
    testImplementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.archTest)

    //compose
    implementation(platform(ComposeLibs.Bom))
    implementation(ComposeLibs.UI)
    implementation(ComposeLibs.Preview)
    implementation(ComposeLibs.Material3)
    implementation(ComposeLibs.Foundation)
    implementation(ComposeLibs.Navigation)
    implementation(ComposeLibs.ViewModel)
    implementation(ComposeLibs.Lifecycle)
    implementation(ComposeLibs.LifecycleRuntime)
    implementation(Dependencies.lifecycleProccess)
    debugImplementation(ComposeLibs.UITooling)
    implementation(ComposeLibs.Coil)

    //hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.activityKtx)

    //navigation
    implementation(Dependencies.navigationUI)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationFragmentKtx)

    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseMessaging)
    implementation(Dependencies.timber)

}