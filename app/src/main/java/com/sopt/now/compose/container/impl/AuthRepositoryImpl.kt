package com.sopt.now.compose.container.impl

import com.sopt.now.compose.container.repository.AuthRepository
import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun postLogin(request: RequestLoginDto): Result<Response<ResponseLoginDto>> =
        runCatching {
            authService.login(request)
        }


    override suspend fun postSignUp(request: RequestSignUpDto): Result<Response<ResponseSignUpDto>> =
        runCatching {
            authService.signUp(request)
        }

}