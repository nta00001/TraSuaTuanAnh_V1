package com.example.trasuatuananh.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VietQRClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vietqr.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val vietQRService: VietQRService by lazy {
        retrofit.create(VietQRService::class.java)
    }

}