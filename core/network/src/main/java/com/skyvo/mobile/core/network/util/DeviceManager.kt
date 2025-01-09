package com.skyvo.mobile.core.network.util

interface DeviceManager {
    fun getApplicationVersionName(): String
    fun getApplicationVersionCode(): Int
    fun getOSVersion(): String
    fun getDeviceName(): String
    fun getDeviceModel(): String
    fun getDeviceId(): String
    fun isDeviceRooted(): Boolean
    fun isEmulator(): Boolean
    fun networkOperatorName(): String
    fun getConnectionType(): String
    fun isNetworkAvailable(): Boolean
    fun isNfcEnabled(): Boolean
    fun isDeviceHasNFC(): Boolean
}