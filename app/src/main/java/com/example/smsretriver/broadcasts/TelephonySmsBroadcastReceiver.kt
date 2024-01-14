package com.example.smsretriver.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

interface TelephonySmsReceivedListener {
    fun smsReceived(smsMessage: SmsMessage)
}

class TelephonySmsBroadcastReceiver(
    private val listener: TelephonySmsReceivedListener
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("tester", "TelephonySmsBroadcastReceiver onReceive() called.")
        intent?.extras?.let {
            printSmsBody(it)
        }
    }

    private fun printSmsBody(bundle: Bundle) {
        val pdus = bundle["pdus"] as Array<*>?
        if (pdus != null) {
            for (pdu in pdus) {
                val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                listener.smsReceived(smsMessage)
            }
        }
    }
}