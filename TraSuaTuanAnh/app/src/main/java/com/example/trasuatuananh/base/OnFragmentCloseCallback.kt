package com.example.trasuatuananh.base

import android.content.Intent

interface OnFragmentCloseCallback {
    fun onClose(resultCode: Int, data: Intent?)
}