package com.example.trasuatuananh.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager

object ActivityUtils {
    fun hideSoftInput(activity: Activity?) {
        if (activity == null || activity.currentFocus == null) {
            return
        }
        val im = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        im?.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

}