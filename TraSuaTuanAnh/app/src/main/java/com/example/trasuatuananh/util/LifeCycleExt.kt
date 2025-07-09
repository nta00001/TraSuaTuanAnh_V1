package com.example.trasuatuananh.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope

object LifeCycleExt {
    fun FragmentActivity.launchWhenCreated(block: () -> Unit) {
        lifecycleScope.launchWhenCreated {
            block()
        }
    }

    fun FragmentActivity.launchWhenStarted(block: () -> Unit) {
        lifecycleScope.launchWhenStarted {
            block()
        }
    }

    fun FragmentActivity.launchWhenResumed(block: () -> Unit) {
        lifecycleScope.launchWhenResumed {
            block()
        }
    }

    fun Fragment.launchWhenCreated(block: () -> Unit) {
        lifecycleScope.launchWhenCreated {
            block()
        }
    }

    fun Fragment.launchWhenStarted(block: () -> Unit) {
        lifecycleScope.launchWhenStarted {
            block()
        }
    }

    fun Fragment.launchWhenResumed(block: () -> Unit) {
        lifecycleScope.launchWhenResumed {
            block()
        }
    }
}