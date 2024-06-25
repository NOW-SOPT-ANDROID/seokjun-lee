package com.sopt.now.container.repository

import com.sopt.now.network.AuthService
import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import com.sopt.now.network.dto.ResponseSignUpDto
import retrofit2.Response

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun postSignUp(request: RequestSignUpDto): Response<ResponseSignUpDto> =
        authService.postSignUp(request)

    override suspend fun getMemberInfo(): Response<ResponseMemberInfoDto> = authService.getMemberInfo()

    override suspend fun postLogin(request: RequestLoginDto) = authService.postLogin(request)

    override suspend fun patchMemberPassword(request: RequestChangePasswordDto): Response<ResponseChangePasswordDto> =
        authService.patchPassword(request)
}