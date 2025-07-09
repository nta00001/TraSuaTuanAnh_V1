package com.example.trasuatuananh.api.model.response

data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val code: Int? = null, // Thêm code
    val data: Any? = null,
    val errors: Any? = null
)
