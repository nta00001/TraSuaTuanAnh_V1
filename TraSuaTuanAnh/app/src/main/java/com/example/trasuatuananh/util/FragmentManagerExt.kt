package com.example.trasuatuananh.util

import androidx.fragment.app.FragmentManager

fun FragmentManager.isFragmentAddedToBackStack(fragmentName: String): Boolean {
    return findBackStackEntry(fragmentName) != null
}

fun FragmentManager.findBackStackEntry(name: String): FragmentManager.BackStackEntry? {
    (backStackEntryCount - 1 downTo 0).forEach {
        if (it >= 0 && getBackStackEntryAt(it).name.equals(name)) {
            return getBackStackEntryAt(it);
        }
    }
    return null
}

fun FragmentManager.getBackStackEntryIndex(name: String): Int {
    (backStackEntryCount - 1 downTo 0).forEach {
        if (it >= 0 && getBackStackEntryAt(it).name.equals(name)) {
            return it
        }
    }
    return -1
}