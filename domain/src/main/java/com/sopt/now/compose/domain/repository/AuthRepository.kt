package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.request.LoginRequestEntity
import com.sopt.now.compose.domain.entity.request.SignupRequestEntity
import com.sopt.now.compose.domain.entity.response.LoginResponseEntity
import com.sopt.now.compose.domain.entity.response.SignupResponseEntity

interface AuthRepository {
    suspend fun postLogin(request: LoginRequestEntity): Result<LoginResponseEntity>
    suspend fun postSignUp(request: SignupRequestEntity): Result<SignupResponseEntity>
}

