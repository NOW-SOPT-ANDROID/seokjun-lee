package com.sopt.now.repository

import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseLoginDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import com.sopt.now.network.dto.ResponseSignUpDto
import retrofit2.Response

interface AuthRepository {
    suspend fun postSignUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
    suspend fun getMemberInfo(): Response<ResponseMemberInfoDto>

    suspend fun postLogin(request: RequestLoginDto): Response<ResponseLoginDto>

    suspend fun patchMemberPassword(request: RequestChangePasswordDto): Response<ResponseChangePasswordDto>
}