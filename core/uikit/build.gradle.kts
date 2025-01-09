plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.skyvo.mobile.core.uikit"
    compileSdk = BuildAndroidConfig.compileSdk

    defaultConfig {
        minSdk = BuildAndroidConfig.minSdk
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeAndroidCompilerVersion
    }
}

dependencies {
    implementation(Dependencies.androidXCore)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.googleMaterial)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesAndroid)

    //Compose
    implementation(platform(ComposeLibs.Bom))
    implementation(ComposeLibs.UI)
    implementation(ComposeLibs.Preview)
    implementation(ComposeLibs.Material)
    implementation(ComposeLibs.Material3)
    implementation(ComposeLibs.Foundation)
    implementation(ComposeLibs.ThemeAdapter)
    implementation(ComposeLibs.SystemUIController)
    implementation(ComposeLibs.Permission)
    implementation(ComposeLibs.Coil)
    debugImplementation(ComposeLibs.UITooling)
    implementation(Dependencies.webKit)
    implementation(Dependencies.timber)
}