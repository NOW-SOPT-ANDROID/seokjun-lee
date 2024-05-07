package com.sopt.now.compose.container

import android.util.Log
import com.sopt.now.compose.models.User
import com.sopt.now.compose.network.TempAuthService
import com.sopt.now.compose.network.dto.RequestChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseChangePasswordDto
import com.sopt.now.compose.network.dto.ResponseMemberInfoDto
import retrofit2.Response

private const val TAG = "AuthRepository"

interface AuthRepository {
    suspend fun getUserInfo(): Result<User>
    suspend fun patchUserPassword(
        request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto>
}

class NetworkAuthRepository(
    private val authService: TempAuthService
): AuthRepository {
    override suspend fun getUserInfo(): Result<User> = runCatching {
        with(authService.getMemberInfo().body()?.data){
            User(
                id = this?.authenticationId.orEmpty(),
                nickName = this?.phone.orEmpty(),
                phone = this?.phone.orEmpty()
            )
        }
    }.onFailure {
        Log.d(TAG, it.message.toString())
    }


    override suspend fun patchUserPassword(
        request: RequestChangePasswordDto
    ): Response<ResponseChangePasswordDto> = authService.changePassword(request = request)

}