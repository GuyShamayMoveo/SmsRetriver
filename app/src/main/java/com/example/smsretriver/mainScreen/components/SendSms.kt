package com.example.smsretriver.mainScreen.components

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


@Composable
fun SendSms(title: String, modifier: Modifier = Modifier, onClick: (Context) -> Unit) {
    val context = LocalContext.current
    Button(onClick = {onClick(context)}, modifier = modifier) {
        Text(text = title)
    }
}
