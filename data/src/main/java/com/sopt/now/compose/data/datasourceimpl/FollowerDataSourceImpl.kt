package com.sopt.now.compose.data.datasourceimpl

import com.sopt.now.compose.data.datasource.FollowerDataSource
import com.sopt.now.compose.data.dto.request.RequestFollowerDto
import com.sopt.now.compose.data.dto.response.ResponseFollowListDto
import com.sopt.now.compose.data.service.FollowerService
import retrofit2.Response
import javax.inject.Inject

class FollowerDataSourceImpl @Inject constructor(
    private val followerService: FollowerService
): FollowerDataSource {

    override suspend fun getFollowerList(request: RequestFollowerDto): Response<ResponseFollowListDto> =
        followerService.getFollow(request.page)
}