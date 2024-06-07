package com.sopt.now.container

import com.sopt.now.container.repository.AuthRepository

interface AppContainer {
    val authRepository: AuthRepository
    val authAfterLoinRepostory: AuthRepository
}