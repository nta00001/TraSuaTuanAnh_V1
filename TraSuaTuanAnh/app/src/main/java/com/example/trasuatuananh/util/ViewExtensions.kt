package com.example.trasuatuananh.util

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.trasuatuananh.widget.listener.OnSingleClickListener

fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
    val childViewStates = SparseArray<Parcelable>()
    children.forEach { child -> child.saveHierarchyState(childViewStates) }
    return childViewStates
}

fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
    children.forEach { child -> child.restoreHierarchyState(childViewStates) }
}

fun ViewGroup.saveInstanceState(state: Parcelable?): Parcelable? {
    return Bundle().apply {
        putParcelable(Constants.BundleConstants.SUPER_STATE, state)
        putSparseParcelableArray(Constants.BundleConstants.SPARSE_STATE_KEY, saveChildViewStates())
    }
}

fun ViewGroup.restoreInstanceState(state: Parcelable?): Parcelable? {
    var newState = state
    if (newState is Bundle) {
        val childrenState =
            newState.getSparseParcelableArray<Parcelable>(Constants.BundleConstants.SPARSE_STATE_KEY)
        childrenState?.let { restoreChildViewStates(it) }
        newState = newState.getParcelable(Constants.BundleConstants.SUPER_STATE)
    }
    return newState
}

fun View.singleClick(listener: (v: View?) -> Unit) {
    this.setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            listener.invoke(v)
        }
    })
}

fun View.paddingBottomNavigationBarsSystem() {
    try {
        ViewCompat.setOnApplyWindowInsetsListener(
            this
        ) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.layoutParams = (view.layoutParams as? FrameLayout.LayoutParams)?.apply {
                bottomMargin = insets.bottom
            }
            WindowInsetsCompat.CONSUMED
        }
    } catch (_: Exception) {
    }
}

