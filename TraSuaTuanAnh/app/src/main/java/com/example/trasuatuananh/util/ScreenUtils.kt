package com.example.trasuatuananh.util

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity

object ScreenUtils {
    private var screenWidthPixels: Int = 0
    private var screenHeightPixels: Int = 0

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dip2px(context: Context?, dipValue: Float): Int {
        if (context == null) return dipValue.toInt()
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2sp(context: Context?, px: Float): Float {
        if (context == null) return px.toInt().toFloat()
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return px / scaledDensity
    }

    fun sp2px(context: Context?, sp: Float): Float {
        if (context == null) return sp
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.displayMetrics)
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun getStatusBarHeight(activity: Activity): Int {
        val rectangle = Rect()
        val window = activity.window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    private fun getActionBarHeight(appCompatActivity: AppCompatActivity): Int {
        val styledAttributes: TypedArray = appCompatActivity.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
        val actionBarHeight = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()
        return actionBarHeight
    }

    fun getHeightBottomSheet(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        return (height * 0.9).toInt()
    }
}