package com.example.trasuatuananh.base

import android.content.BroadcastReceiver

abstract class LocalBroadcastReceiver : BroadcastReceiver() {

    protected var mConsumeBroadcast: Boolean = false

    fun isBroadcastConsumed(): Boolean {
        return mConsumeBroadcast
    }

    fun consumeBroadcast() {
        mConsumeBroadcast = true
    }

    fun clearConsumeBroadcast() {
        mConsumeBroadcast = false
    }
}