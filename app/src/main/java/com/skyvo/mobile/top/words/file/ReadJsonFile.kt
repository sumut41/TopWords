package com.skyvo.mobile.top.words.file

import android.content.Context
import com.skyvo.mobile.core.base.manager.AppWordParentModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader

class ReadJsonFile(private val context: Context) {
    private fun readJsonFromRaw(resourceId: Int): String {
        return context.resources.openRawResource(resourceId).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
        }
    }

    fun parseJson(resourceId: Int): AppWordParentModel? {
        val json = readJsonFromRaw(resourceId)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter = moshi.adapter(AppWordParentModel::class.java)
        return jsonAdapter.fromJson(json)
    }
}