package com.sopt.now.network

import com.sopt.now.network.dto.ResponseFollowListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowService {
    @GET("/api/users")
    suspend fun getFollower(
        @Query("page") page:Int
    ):ResponseFollowListDto
}