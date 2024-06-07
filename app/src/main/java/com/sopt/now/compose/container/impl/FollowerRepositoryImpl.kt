package com.sopt.now.compose.container.impl

import com.sopt.now.compose.container.repository.FollowerRepository
import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Response

class FollowerRepositoryImpl(
    private val followService: FollowService
): FollowerRepository {
    override suspend fun getFollowers(page: Int): Result<Response<ResponseFollowListDto>> = runCatching {
        followService.getFollow(page)
    }
}