package com.sopt.now.compose

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.sopt.now.compose.container.AppContainer
import com.sopt.now.compose.container.SoptAppContainer

class SoptApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = SoptAppContainer(applicationContext)
    }
}