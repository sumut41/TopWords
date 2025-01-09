package com.skyvo.mobile.core.uikit.theme

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class ThemeUtils {
    companion object {
        private fun getAttributeResourceId(context: Context, attr: Int): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attr, typedValue, true)
            return typedValue.resourceId
        }

        fun getAttributeColor(context: Context, @AttrRes attr: Int): Int {
            val colorRes = getAttributeResourceId(context, attr)
            var color = -1

            try {
                color = ContextCompat.getColor(context, colorRes)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return color
        }

        @Synchronized
        fun setAppTheme(isNightMode: Boolean?) {
            if (isNightMode == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}