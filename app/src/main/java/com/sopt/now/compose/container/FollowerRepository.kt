package com.sopt.now.compose.container

import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Response

interface FollowerRepository{
    suspend fun getFollowers(page: Int = 2): Result<Response<ResponseFollowListDto>>
}

class NetworkFollowerRepository(
    private val followService: FollowService
): FollowerRepository {
    override suspend fun getFollowers(page: Int): Result<Response<ResponseFollowListDto>> = runCatching {
        followService.getFollow(page)
    }
}