package com.sopt.now.compose.data.datasource

import com.sopt.now.compose.data.dto.request.RequestFollowerDto
import com.sopt.now.compose.data.dto.response.ResponseFollowListDto
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollowerList(request: RequestFollowerDto): Response<ResponseFollowListDto>
}