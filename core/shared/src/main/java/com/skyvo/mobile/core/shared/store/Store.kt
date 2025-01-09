package com.skyvo.mobile.core.shared.store

class Store {

    var state: MutableMap<String, Any> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <U: Any> findStateValue(stateId: String): U? = state[stateId] as? U

    fun setStateValue(stateId: String, value: Any) {
        shared.state[stateId] = value
    }

    fun setStringValue(key: String, value: String) {
        shared.state[key] = value
    }

    fun setIntValue(key: String, value: Int) {
        shared.state[key] = value
    }

    fun setDoubleValue(key: String, value: Double) {
        shared.state[key] = value
    }

    fun setBooleanValue(key: String, value: Boolean) {
        shared.state[key] = value
    }

    fun getStringValue(key: String): String? =
        shared.state[key] as? String

    fun getIntValue(key: String): Int? =
        shared.state[key] as? Int

    fun getDoubleValue(key: String): Double? =
        shared.state[key] as? Double

    fun getBooleanValue(key: String): Boolean? =
        shared.state[key] as? Boolean

    companion object {
        val shared = Store()
    }
}