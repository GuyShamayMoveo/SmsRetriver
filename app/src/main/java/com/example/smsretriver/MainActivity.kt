package com.example.smsretriver

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
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
import com.example.smsretriver.broadcast.SmsReceiver
import com.example.smsretriver.mainScreen.MainScreen
import com.example.smsretriver.ui.theme.SmsretriverTheme
import com.google.android.gms.auth.api.phone.SmsRetriever


private const val REQUEST_CODE = 13213

class MainActivity : ComponentActivity() {


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
    }

    private val broadCastReceiver = SmsReceiver()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        registerReceiver(broadCastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            RECEIVER_NOT_EXPORTED
        )

    }


    private fun requestSmsPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                "Manifest.permission.READ_SMS",
                "Manifest.permission.SEND_SMS",
                "Manifest.permission.RECEIVE_SMS"
            ),
            REQUEST_CODE
        );
    }
}

@Preview(showBackground = true)
@Composable
fun AwesomeButton() {
    SmsretriverTheme {
        MainScreen()
    }
}



