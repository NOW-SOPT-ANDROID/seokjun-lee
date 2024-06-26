package com.sopt.now.compose

import android.app.Application
import com.sopt.now.compose.container.AppContainer
import com.sopt.now.compose.container.SoptAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SoptApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = SoptAppContainer(applicationContext)
    }

}