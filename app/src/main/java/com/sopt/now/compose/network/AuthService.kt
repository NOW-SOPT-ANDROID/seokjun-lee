package com.sopt.now.compose.network

import com.sopt.now.compose.network.dto.RequestChangePasswordDto
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    suspend fun signUp(
        @Body request: RequestSignUpDto,
    ): Response<ResponseSignUpDto>

    @POST("member/login")
    suspend fun login(
        @Body request: RequestLoginDto
    ): Response<ResponseLoginDto>

    @GET("member/info")
    suspend fun getMemberInfo(): Response<ResponseMemberInfoDto>

    @PATCH("member/password")
    suspend fun changePassword(
        @Body request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto>
}