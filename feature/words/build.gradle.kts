plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.navigationSafeargs)
}

android {
    namespace = "com.skyvo.mobile.top.words.feature.words"
    compileSdk = BuildAndroidConfig.compileSdk

    defaultConfig {
        minSdk = BuildAndroidConfig.minSdk
        vectorDrawables.useSupportLibrary = true
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeAndroidCompilerVersion
    }
}

dependencies {
    implementation(project(BuildModules.CORE.BASE))
    implementation(project(BuildModules.CORE.UIKIT))
    implementation(project(BuildModules.CORE.DATABASE))
    implementation(project(BuildModules.CORE.RESOURCE))

    implementation(Dependencies.androidXCore)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.googleMaterial)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesAndroid)

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
    implementation(ComposeLibs.LifecycleRuntime)
    debugImplementation(ComposeLibs.UITooling)
    implementation(ComposeLibs.Coil)

    //hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.fragmentKtx)

    //navigation
    implementation(Dependencies.navigationFragment)
}