package com.sopt.now.compose.container.repository

import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Response

interface FollowerRepository{
    suspend fun getFollowers(page: Int = 2): Result<Response<ResponseFollowListDto>>
}

