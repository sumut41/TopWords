plugins {
    id(Plugins.kotlinAndroid) version Versions.kotlinVersion apply false
    id(Plugins.androidApplication) version Versions.androidApplication apply false
    id(Plugins.artifactory) version Versions.artifactory
    id(Plugins.pluginSerialization) version Versions.kotlinVersion apply false
    id(Plugins.googleService) version Versions.googleServicePlugin apply false
    id(Plugins.navigationSafeargs) version Versions.navigationSafeargs apply false
    id(Plugins.androidLibrary) version Versions.androidApplication apply false
    id(Plugins.hilt) version Versions.hiltAndroid apply false
}

buildscript {
    dependencies {
        classpath(Plugins.kotlinClassPath)
        classpath(Plugins.firebaseCrashlyticsClassPath)
    }
}