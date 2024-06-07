package com.sopt.now.container.repository

import com.sopt.now.network.AuthService
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import com.sopt.now.network.dto.ResponseSignUpDto
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun postSignUp(request: RequestSignUpDto): Response<ResponseSignUpDto> =
        authService.postSignUps(request)

    override suspend fun getMemberInfo(): Response<ResponseMemberInfoDto> = authService.getMemberInfos()

    override suspend fun postLogin(request: RequestLoginDto) = authService.postLogins(request)

    override suspend fun patchMemberPassword(request: RequestChangePasswordDto): Response<ResponseChangePasswordDto> =
        authService.patchPasswords(request)
}