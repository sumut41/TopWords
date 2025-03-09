package com.skyvo.mobile.top.words.file

import android.annotation.SuppressLint
import android.content.Context
import com.skyvo.mobile.core.base.manager.AppWordParentModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class ReadJsonFile(private val context: Context) {
    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val jsonAdapter by lazy {
        moshi.adapter(AppWordParentModel::class.java)
    }

    private suspend fun readJsonFromRaw(resourceId: Int): String = withContext(Dispatchers.IO) {
        context.resources.openRawResource(resourceId).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
        }
    }

    suspend fun parseJson(resourceId: Int): AppWordParentModel? = withContext(Dispatchers.IO) {
        val json = readJsonFromRaw(resourceId)
        jsonAdapter.fromJson(json)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ReadJsonFile? = null

        fun getInstance(context: Context): ReadJsonFile {
            return instance ?: synchronized(this) {
                instance ?: ReadJsonFile(context.applicationContext).also { instance = it }
            }
        }
    }
}