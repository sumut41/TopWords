object Dependencies {
    const val androidXCore = "androidx.core:core-ktx:${Versions.coreVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val lifecycleProccess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycleProcess}"
    const val googleMaterial = "com.google.android.material:material:${Versions.googleMaterial}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewModelScope = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleScope = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    // Unit Test
    const val junitTest = "junit:junit:${Versions.junitTest}"
    const val androidxJunitTest = "androidx.test.ext:junit:${Versions.androidxJunitTest}"
    const val espressoTest = "androidx.test.espresso:espresso-core:${Versions.espressoTest}"
    const val mockkTest = "io.mockk:mockk:${Versions.mockkVersion}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTestVersion}"
    const val turbineTest = "app.cash.turbine:turbine:${Versions.turbineTestVersion}"
    const val archTest = "androidx.arch.core:core-testing:2.2.0"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitMoshi}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    // hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val supportConstraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigationFragment}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationFragment}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCrypto}"

    // chucker
    const val chuckerDebug = "com.github.chuckerteam.chucker:library:${Versions.chuckerVersion}"
    const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chuckerVersion}"

    // firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseRemoteConfig = "com.google.firebase:firebase-config-ktx"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val pdfViewer = "com.github.barteksc:android-pdf-viewer:2.8.2"
    const val webKit = "androidx.webkit:webkit:${Versions.webKitVersion}"

    // room
    const val Room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val RoomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val RoomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

}