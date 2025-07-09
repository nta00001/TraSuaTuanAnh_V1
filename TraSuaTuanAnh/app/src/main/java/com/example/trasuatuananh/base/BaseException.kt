package com.example.trasuatuananh.base

class BaseException(
    val code: Int,
    override val message: String
) : RuntimeException(message)
