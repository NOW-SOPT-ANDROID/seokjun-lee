package com.sopt.now.compose.data.datasourceimpl

import com.sopt.now.compose.data.datasource.AuthDataSource
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import com.sopt.now.compose.data.dto.response.ResponseLoginDto
import com.sopt.now.compose.data.dto.response.ResponseSignUpDto
import com.sopt.now.compose.data.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
): AuthDataSource {

    override suspend fun postLogin(request: RequestLoginDto): Response<ResponseLoginDto> =
        authService.postLogin(request)

    override suspend fun postSignUp(request: RequestSignUpDto): Response<ResponseSignUpDto> =
        authService.postSignup(request)
}