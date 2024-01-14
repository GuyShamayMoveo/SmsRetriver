package com.example.smsretriver.mainScreen

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.smsretriver.mainScreen.components.Greeting
import com.example.smsretriver.mainScreen.components.SendSms
import com.google.android.gms.auth.api.phone.SmsRetriever

typealias VoidCallback = () -> Unit

@Composable
fun MainScreen() {
    var codeInput = remember { "" }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Greeting("Android")
        TextField(
            value = codeInput,
            onValueChange = {
                codeInput = it
            },
            leadingIcon = {
                Icons.Rounded.Build
            }
        )
        SendSms(title = "Send SMS") {
            sendSMS("+972502621713", "fff", it)
//            val intent = Intent(SmsRetriever.SMS_RETRIEVED_ACTION)
//            intent.putExtra("sms_body", "default content")
//            it.startActivity(intent)
        }
    }
}

@Composable
fun sendDummySMSIntent() {
    val intent = Intent(SmsRetriever.SMS_RETRIEVED_ACTION)
    intent.putExtra("sms_body", "default content")
    LocalContext.current.startActivity(intent)
}

private fun sendSMS(phoneNumber: String, message: String, context: Context) {
    val sentPI: PendingIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"),
        PendingIntent.FLAG_IMMUTABLE)
    SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
}

