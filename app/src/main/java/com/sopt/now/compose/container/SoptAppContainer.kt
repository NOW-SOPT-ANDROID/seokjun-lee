package com.sopt.now.compose.container

import android.content.Context

interface AppContainer{
    val userRepository: PreferenceUserRepository
}

class SoptAppContainer(context: Context): AppContainer {
    override val userRepository: PreferenceUserRepository by lazy {
        PreferenceUserRepository(context.getSharedPreferences("SOPT", Context.MODE_PRIVATE))
    }
}