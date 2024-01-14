package com.example.smsretriver.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsRetrieverBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("tester", "SmsRetrievedBroadcastReceiver onReceive() called.")
        val x = 5 + 5
    }
}