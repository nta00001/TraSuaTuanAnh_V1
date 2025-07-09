package com.example.trasuatuananh.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.trasuatuananh.databinding.LayoutCustomToastBinding


object CommonToast {

    fun showToast(
        context: Context,
        message: String,
        iconResId: Int,
        gravity: Int,
        xOffset: Int,
        yOffset: Int
    ) {
        val binding = LayoutCustomToastBinding.inflate(LayoutInflater.from(context))


        // Thiết lập icon và message
        binding.toastIcon.setImageResource(iconResId)
        binding.toastText.text = message

        // Tạo Toast với layout tùy chỉnh
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = binding.root

        // Đặt vị trí hiển thị cho Toast
        toast.setGravity(gravity, xOffset, yOffset)

        // Hiển thị Toast
        toast.show()
    }

    fun showToast(
        context: Context,
        message: String,
        iconResId: Int
    ) {
        showToast(context, message, iconResId, Gravity.TOP, 0, 100)
    }
}