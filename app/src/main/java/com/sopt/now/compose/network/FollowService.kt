package com.sopt.now.compose.network

import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowService {
    @GET("/api/users")
    suspend fun getFollow(
        @Query("page") page:Int
    ): ResponseFollowListDto
}