package com.sopt.now.container

import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.container.repository.FollowerRepository

interface AppContainer {
    val authRepository: AuthRepository
    val authAfterLoinRepository: AuthRepository
    val followerRepository: FollowerRepository
}