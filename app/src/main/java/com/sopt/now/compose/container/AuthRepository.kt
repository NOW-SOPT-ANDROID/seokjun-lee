package com.sopt.now.compose.container

import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import com.sopt.now.compose.ui.screens.login.LoginViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.Response

interface AuthRepository {
    suspend fun postLogin(request: RequestLoginDto): Result<Response<ResponseLoginDto>>
    suspend fun postSignUp(request: RequestSignUpDto): Result<Response<ResponseSignUpDto>>
}

class NetworkAuthRepository(
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