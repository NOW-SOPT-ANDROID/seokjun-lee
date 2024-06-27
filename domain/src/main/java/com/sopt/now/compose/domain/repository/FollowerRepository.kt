package com.sopt.now.compose.domain.repository

import com.sopt.now.compose.domain.entity.request.FollowerRequestEntity
import com.sopt.now.compose.domain.entity.response.FollowerResponseEntity
import retrofit2.Response

interface FollowerRepository{
    suspend fun getFollowers(request: FollowerRequestEntity): Result<List<FollowerResponseEntity>>
}

