package com.sopt.now.compose.container

import com.sopt.now.compose.container.impl.AuthRepositoryImpl
import com.sopt.now.compose.container.impl.FollowerRepositoryImpl
import com.sopt.now.compose.container.impl.MemberRepositoryImpl
import com.sopt.now.compose.container.impl.UserRepositoryImpl

interface AppContainer{
    val userRepository: UserRepositoryImpl
    val followRepository: FollowerRepositoryImpl
    val memberRepository: MemberRepositoryImpl
    val authRepository: AuthRepositoryImpl
}