package com.example.smsretriver.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class TelephonySmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("tester", "TelephonySmsBroadcastReceiver onReceive() called.")
        intent?.extras?.let {
            printSmsBody(it)
        }
    }

    private fun printSmsBody(bundle: Bundle) {
        // Retrieve the SMS message
        val pdus = bundle["pdus"] as Array<Any>?
        if (pdus != null) {
            for (pdu in pdus) {
                val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdu as ByteArray)

                // Get the SMS body
                val smsBody: String = smsMessage.getMessageBody()
                Log.d("SmsReceiver", "SMS Body: $smsBody")
            }
        }
    }
}