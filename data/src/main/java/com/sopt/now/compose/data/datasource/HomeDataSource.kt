package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.response.ResponseUserDto
import retrofit2.Response

interface HomeDataSource {
    suspend fun getUserInfo(): Response<ResponseUserDto>
}