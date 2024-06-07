package com.sopt.now.container.repository

import com.sopt.now.network.dto.ResponseFollowListDto

interface FollowerRepository {
    suspend fun getFollowerList(page: Int): ResponseFollowListDto
}