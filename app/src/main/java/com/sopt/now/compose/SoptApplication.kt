package com.sopt.now.compose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SoptApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}