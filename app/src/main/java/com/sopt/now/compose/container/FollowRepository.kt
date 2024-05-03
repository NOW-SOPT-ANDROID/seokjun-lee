package com.sopt.now.compose.container

import android.util.Log
import com.sopt.now.compose.models.Follow
import com.sopt.now.compose.models.Friend
import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.dto.ResponseFollowListDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowRepository(
    private val followService: FollowService
) {
    suspend fun fetchFollow(page: Int = 2): List<Follow>? {
        val response = followService.getFollow(page)
        return response.data.ifEmpty { null }
    }
}