package com.sopt.now.compose.presentation.screens.search

import android.content.Context
import android.widget.Toast

class SearchViewModel {

    companion object {
        const val NAVIGATE_SIGNUP_KEY = "user"
        const val NAVIGATE_LOGIN_KEY = "login"

        const val NAVIGATE_BACK_PRESSED_KEY = "back"

        fun printToastMessage(context: Context, messageRes: Int) {
            Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
        }

    }
}