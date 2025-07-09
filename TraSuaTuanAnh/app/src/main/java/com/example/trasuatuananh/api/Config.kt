package com.example.trasuatuananh.api

object Config {
    const val sectionTimeout = 300000 // 5 minute
    const val urlImage = "https://reptile-great-poorly.ngrok-free.app"

    const val BANK_ID = 970415
    const val ACCOUNT_NUMBER = "103870442864"
    const val ACCOUNT_NAME = "PHAM HOANG HAI"
    const val DESCRIPTION = "Thanh toan tien tra sua"
    const val ACCOUNT_ADMIN = "0975718004"
    const val PASSWORD_ADMIN = "1234567"

    fun getImageUrl(id: String): String {
        return urlImage.replace("id", id)
    }

}