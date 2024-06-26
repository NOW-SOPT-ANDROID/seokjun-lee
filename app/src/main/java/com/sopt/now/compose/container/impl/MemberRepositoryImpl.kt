package com.sopt.now.compose.container.impl

import com.sopt.now.compose.container.repository.MemberRepository
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.dto.RequestChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseChangePasswordDto
import retrofit2.Response
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : MemberRepository {
    override suspend fun getUserInfo(): Result<User> = runCatching {
        with(authService.getMemberInfo().body()?.data) {
            User(
                id = this?.authenticationId.orEmpty(),
                nickName = this?.nickname.orEmpty(),
                phone = this?.phone.orEmpty()
            )
        }
    }

    override suspend fun patchUserPassword(
        request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto> = authService.changePassword(request = request)

}