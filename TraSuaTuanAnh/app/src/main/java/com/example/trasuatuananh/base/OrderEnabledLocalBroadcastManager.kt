package com.example.trasuatuananh.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Looper
import android.os.Message
import android.util.Log
import java.util.Arrays
import android.os.Handler

class OrderEnabledLocalBroadcastManager private constructor(context: Context) {

    private val mAppContext = context.applicationContext

    private val mReceivers = HashMap<LocalBroadcastReceiver, ArrayList<IntentFilter>>()
    private val mActions = HashMap<String, ArrayList<ReceiverRecord>>()
    private val mPendingBroadcasts = ArrayList<BroadcastRecord>()

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_EXEC_PENDING_BROADCASTS) {
                executePendingBroadcasts()
            } else {
                super.handleMessage(msg)
            }
        }
    }

    private val mLock = Any()
    private var mInstance: OrderEnabledLocalBroadcastManager? = null

    fun registerReceiver(receiver: LocalBroadcastReceiver, filter: IntentFilter) {
        synchronized(mReceivers) {
            val entry = ReceiverRecord(filter, receiver)
            val filters = mReceivers[receiver] ?: ArrayList()
            mReceivers[receiver] = filters.apply { add(filter) }

            for (i in 0 until filter.countActions()) {
                val action = filter.getAction(i)
                val entries = mActions[action] ?: ArrayList()
                mActions[action] = entries.apply { add(entry) }
            }
        }
    }

    fun unregisterReceiver(receiver: LocalBroadcastReceiver) {
        synchronized(mReceivers) {
            val filters = mReceivers.remove(receiver) ?: return
            for (filter in filters) {
                for (i in 0 until filter.countActions()) {
                    val action = filter.getAction(i)
                    val receivers = mActions[action]
                    receivers?.removeIf { it.receiver == receiver }
                    if (receivers != null && receivers.isEmpty()) {
                        mActions.remove(action)
                    }
                }
            }
        }
    }

    private fun sendBroadcast(intent: Intent, isOrdered: Boolean): Boolean {
        synchronized(mReceivers) {
            val action = intent.action ?: return false
            val type = intent.resolveTypeIfNeeded(mAppContext.contentResolver)
            val data: Uri? = intent.data
            val scheme: String? = intent.scheme
            val categories = intent.categories

            val debug = DEBUG || (intent.flags and Intent.FLAG_DEBUG_LOG_RESOLUTION) != 0
            if (debug) {
                Log.v(TAG, "Resolving type $type scheme $scheme of intent $intent")
            }

            val entries = mActions[action] ?: return false
            val receivers = ArrayList<ReceiverRecord>()

            for (receiver in entries) {
                if (receiver.broadcasting) {
                    if (debug) {
                        Log.v(TAG, "Filter's target already added")
                    }
                    continue
                }

                val match = receiver.filter.match(
                    action,
                    type,
                    scheme,
                    data,
                    categories,
                    "LocalBroadcastManager"
                )
                if (match >= 0) {
                    if (debug) {
                        Log.v(TAG, "Filter matched! match=0x${Integer.toHexString(match)}")
                    }
                    receivers.add(receiver)
                    receiver.broadcasting = true
                } else {
                    if (debug) {
                        Log.v(TAG, "Filter did not match: ${getMatchReason(match)}")
                    }
                }
            }

            if (receivers.isNotEmpty()) {
                receivers.forEach { it.broadcasting = false }
                mPendingBroadcasts.add(BroadcastRecord(intent, receivers, isOrdered))
                if (!mHandler.hasMessages(MSG_EXEC_PENDING_BROADCASTS)) {
                    mHandler.sendEmptyMessage(MSG_EXEC_PENDING_BROADCASTS)
                }
                return true
            }
        }
        return false
    }

    fun sendBroadcast(intent: Intent): Boolean {
        return sendBroadcast(intent, false)
    }

    fun sendOrderedBroadcast(intent: Intent): Boolean {
        return sendBroadcast(intent, true)
    }

    fun sendBroadcastSync(intent: Intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts()
        }
    }

    private fun executePendingBroadcasts() {
        while (true) {
            val brs = synchronized(mReceivers) {
                if (mPendingBroadcasts.isEmpty()) return
                mPendingBroadcasts.toTypedArray().also { mPendingBroadcasts.clear() }
            }

            for (br in brs) {
                if (br.isOrdered) {
                    val receiversAsArray = br.receivers.toTypedArray()
                    Arrays.sort(receiversAsArray, ReceiverRecordComparator())

                    for (i in receiversAsArray.indices.reversed()) {
                        val receiver = receiversAsArray[i].receiver
                        receiver.onReceive(mAppContext, br.intent)
                        if (receiver.isBroadcastConsumed()) break
                    }
                } else {
                    br.receivers.forEach { it.receiver.onReceive(mAppContext, br.intent) }
                }
            }
        }
    }

    companion object {
        private const val TAG = "OrderEnabledLB"
        private const val MSG_EXEC_PENDING_BROADCASTS = 1
        private const val DEBUG = false

        @Volatile
        private var mInstance: OrderEnabledLocalBroadcastManager? = null

        fun getInstance(context: Context): OrderEnabledLocalBroadcastManager {
            return mInstance ?: synchronized(this) {
                mInstance ?: OrderEnabledLocalBroadcastManager(context).also { mInstance = it }
            }
        }

        private fun getMatchReason(match: Int): String {
            return when (match) {
                IntentFilter.NO_MATCH_ACTION -> "action"
                IntentFilter.NO_MATCH_CATEGORY -> "category"
                IntentFilter.NO_MATCH_DATA -> "data"
                IntentFilter.NO_MATCH_TYPE -> "type"
                else -> "unknown reason"
            }
        }
    }

    private data class BroadcastRecord(
        val intent: Intent,
        val receivers: List<ReceiverRecord>,
        val isOrdered: Boolean
    )

    private class ReceiverRecord(val filter: IntentFilter, val receiver: LocalBroadcastReceiver) {
        var broadcasting = false

        override fun toString(): String {
            return "Receiver{receiver=$receiver filter=$filter}"
        }
    }

    private class ReceiverRecordComparator : Comparator<ReceiverRecord> {
        override fun compare(o1: ReceiverRecord, o2: ReceiverRecord): Int {
            return o1.filter.priority - o2.filter.priority
        }
    }
}

