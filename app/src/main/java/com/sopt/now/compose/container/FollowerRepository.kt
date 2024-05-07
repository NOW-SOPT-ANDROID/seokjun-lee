package com.sopt.now.compose.container

import com.sopt.now.compose.models.Follower
import com.sopt.now.compose.network.FollowService

interface FollowerRepository{
    suspend fun fetchFollow(page: Int = 2): List<Follower>?
}

class NetworkFollowerRepository(
    private val followService: FollowService
): FollowerRepository {
    override suspend fun fetchFollow(page: Int): List<Follower> {
        val response = followService.getFollow(page)
        return response.data
    }
}