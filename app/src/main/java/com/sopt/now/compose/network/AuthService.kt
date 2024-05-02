package com.sopt.now.compose.network

import com.sopt.now.compose.network.dto.RequestChangePasswordDto
import com.sopt.now.compose.network.dto.RequestLoginDto
import com.sopt.now.compose.network.dto.RequestSignUpDto
import com.sopt.now.compose.network.dto.ResponseChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseLoginDto
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import com.sopt.now.compose.network.dto.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>

    @GET("member/info")
    fun getMemberInfo(): Call<ResponseMemberInfoDto>

    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>

    @PATCH("member/password")
    fun changePassword(
        @Body request: RequestChangePasswordDto
    ): Call<ResponseChangePasswordDto>
}