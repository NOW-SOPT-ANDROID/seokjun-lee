package com.sopt.now.compose.container.repository

import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import retrofit2.Response

interface AuthRepository {
    suspend fun postLogin(request: RequestLoginDto): Result<Response<ResponseLoginDto>>
    suspend fun postSignUp(request: RequestSignUpDto): Result<Response<ResponseSignUpDto>>
}

