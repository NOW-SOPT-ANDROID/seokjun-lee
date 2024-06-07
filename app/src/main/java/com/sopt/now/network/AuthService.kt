package com.sopt.now.network

import com.sopt.now.network.dto.RequestChangePasswordDto
import com.sopt.now.network.dto.RequestLoginDto
import com.sopt.now.network.dto.RequestSignUpDto
import com.sopt.now.network.dto.ResponseChangePasswordDto
import com.sopt.now.network.dto.ResponseLoginDto
import com.sopt.now.network.dto.ResponseMemberInfoDto
import com.sopt.now.network.dto.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    fun postSignUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>

    @GET("member/info")
    fun getMemberInfo(): Call<ResponseMemberInfoDto>

    @PATCH("member/password")
    fun patchPassword(
        @Body request: RequestChangePasswordDto
    ): Call<ResponseChangePasswordDto>

    /********/
    @POST("member/join")
    suspend fun postSignUps(
        @Body request: RequestSignUpDto,
    ): Response<ResponseSignUpDto>

    @GET("member/info")
    suspend fun getMemberInfos(): Response<ResponseMemberInfoDto>

    @POST("member/login")
    suspend fun postLogins(
        @Body request: RequestLoginDto
    ): Response<ResponseLoginDto>

    @PATCH("member/password")
    suspend fun patchPasswords(
        @Body request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto>
}