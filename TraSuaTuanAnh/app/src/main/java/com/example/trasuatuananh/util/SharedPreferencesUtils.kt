package com.example.trasuatuananh.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {

    private const val PREFS_NAME = "AppPreferences"
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun put(key: String, value: Any) {
        sharedPreferences?.edit()?.apply {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        } ?: throw IllegalStateException("SharedPreferencesUtils is not initialized")
    }

    internal inline fun <reified T> get(key: String, defaultValue: T): T {
        return sharedPreferences?.let {
            when (T::class) {
                String::class -> it.getString(key, defaultValue as? String ?: "") as T
                Int::class -> it.getInt(key, defaultValue as? Int ?: 0) as T
                Boolean::class -> it.getBoolean(key, defaultValue as? Boolean ?: false) as T
                Float::class -> it.getFloat(key, defaultValue as? Float ?: 0f) as T
                Long::class -> it.getLong(key, defaultValue as? Long ?: 0L) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        } ?: throw IllegalStateException("SharedPreferencesUtils is not initialized")
    }



    fun remove(key: String) {
        sharedPreferences?.edit()?.remove(key)?.apply()
            ?: throw IllegalStateException("SharedPreferencesUtils is not initialized")
    }

    fun clear() {
        sharedPreferences?.edit()?.clear()?.apply()
            ?: throw IllegalStateException("SharedPreferencesUtils is not initialized")
    }
}
