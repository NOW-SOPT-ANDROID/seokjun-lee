package com.sopt.now

import android.app.Application
import com.sopt.now.container.AppContainer
import com.sopt.now.container.SoptAppContainer

class SoptApplication: Application() {
    private lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = SoptAppContainer(this@SoptApplication)
    }
}