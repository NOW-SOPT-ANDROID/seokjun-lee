package com.sopt.now.compose.data.service

import com.sopt.now.compose.data.dto.response.ResponseFollowListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("/api/users")
    suspend fun getFollow(
        @Query("page") page:Int = 2
    ): Response<ResponseFollowListDto>
}