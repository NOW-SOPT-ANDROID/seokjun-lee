package com.sopt.now.compose.data.service


import com.sopt.now.compose.data.dto.response.ResponseUserDto
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("member/info")
    suspend fun getMemberInfo(): Response<ResponseUserDto>
}