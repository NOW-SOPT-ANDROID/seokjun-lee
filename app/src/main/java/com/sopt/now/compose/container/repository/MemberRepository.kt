package com.sopt.now.compose.container.repository

import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.dto.RequestChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseChangePasswordDto
import retrofit2.Response

interface MemberRepository {
    suspend fun getUserInfo(): Result<User>
    suspend fun patchUserPassword(
        request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto>
}