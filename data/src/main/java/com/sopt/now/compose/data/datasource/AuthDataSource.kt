package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import com.sopt.now.compose.data.dto.response.ResponseSignUpDto
import retrofit2.Response

interface AuthDataSource {
    suspend fun postLogin(request: RequestLoginDto): Response<ResponseLoginDto>
    suspend fun postSignUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
}