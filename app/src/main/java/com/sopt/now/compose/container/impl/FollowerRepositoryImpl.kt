package com.sopt.now.compose.container.impl

import com.sopt.now.compose.container.repository.FollowerRepository
import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Response
import javax.inject.Inject

class FollowerRepositoryImpl @Inject constructor(
    private val followService: FollowService
): FollowerRepository {
    override suspend fun getFollowers(page: Int): Result<Response<ResponseFollowListDto>> = runCatching {
        followService.getFollow(page)
    }
}