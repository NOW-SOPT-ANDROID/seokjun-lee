package com.sopt.now.container.repository

import com.sopt.now.network.FollowService
import com.sopt.now.network.dto.ResponseFollowListDto

class FollowerRepositoryImpl(
    private val followService: FollowService
): FollowerRepository {
    override suspend fun getFollowerList(page: Int): ResponseFollowListDto =
        followService.getFollower(page)
}