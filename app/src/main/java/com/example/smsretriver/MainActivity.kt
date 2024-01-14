package com.example.smsretriver

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smsretriver.broadcasts.SmsRetrieverBroadcastReceiver
import com.example.smsretriver.broadcasts.TelephonySmsBroadcastReceiver
import com.example.smsretriver.mainScreen.MainScreen
import com.example.smsretriver.ui.theme.SmsretriverTheme
import com.google.android.gms.auth.api.phone.SmsRetriever


private const val REQUEST_CODE = 13213

class MainActivity : ComponentActivity() {

    private val telephonyBroadcastReceiver = TelephonySmsBroadcastReceiver()
    private val smsRetrieverBroadcastReceiver = SmsRetrieverBroadcastReceiver()

    private val smsPermissions = arrayOf(
        "Manifest.permission.READ_SMS",
        "Manifest.permission.SEND_SMS",
        "Manifest.permission.RECEIVE_SMS"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsretriverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        MainScreen()
                        Button(onClick = {
                            requestSmsPermissions()
                        }) {
                            Text(text = "Request permission")
                        }
                    }

                }
            }
        }

        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        requestSmsPermissions()
        val isGranted = isAllPermissionsGranted()
        Log.i("tester", "onCreate: $isGranted")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        registerBroadcastReceivers()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerBroadcastReceivers() {
        registerReceiver(
            telephonyBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION),
            RECEIVER_NOT_EXPORTED
        )
        registerReceiver(
            smsRetrieverBroadcastReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            RECEIVER_NOT_EXPORTED
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        unregisterBroadcastReceiver()
    }

    private fun unregisterBroadcastReceiver() {
        unregisterReceiver(telephonyBroadcastReceiver)
        unregisterReceiver(smsRetrieverBroadcastReceiver)
    }

    private fun requestSmsPermissions() {
        ActivityCompat.requestPermissions(this, smsPermissions, REQUEST_CODE)
    }

    private fun isAllPermissionsGranted(): Boolean {
        for (permission in smsPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}

@Preview(showBackground = true)
@Composable
fun AwesomeButton() {
    SmsretriverTheme {
        MainScreen()
    }
}



