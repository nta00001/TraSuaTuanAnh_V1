package com.example.trasuatuananh.ui.myapplication

import TokenManager
import android.app.Application
import android.content.Context
import com.example.trasuatuananh.util.SharedPreferencesUtils

class MyApplication : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this // Gán context của ứng dụng
        TokenManager.init(this) // Khởi tạo TokenManager
        SharedPreferencesUtils.init(this) // Khởi tạo SharedPreferencesUtils
    }
}

