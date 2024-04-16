package com.sopt.now.compose

import android.app.Application
import com.sopt.now.compose.container.AppContainer
import com.sopt.now.compose.container.SoptAppContainer

class SoptApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = SoptAppContainer(applicationContext)
    }

    companion object{
        const val NAVIGATE_SIGNUP_KEY = "user"
        const val NAVIGATE_LOGIN_KEY = "login"

        const val NAVIGATE_BACK_PRESSED_KEY = "back"

    }
}