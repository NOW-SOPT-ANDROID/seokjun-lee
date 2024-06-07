package com.sopt.now.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.sopt.now.compose.ui.SoptApp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SoptApp()
                }
            }
        }
    }
    companion object {
        const val NAVIGATE_SIGNUP_KEY = "user"
        const val NAVIGATE_LOGIN_KEY = "login"

        const val NAVIGATE_BACK_PRESSED_KEY = "back"

        fun printToastMessage(context: Context, messageRes: Int) {
            Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
        }

    }
}
