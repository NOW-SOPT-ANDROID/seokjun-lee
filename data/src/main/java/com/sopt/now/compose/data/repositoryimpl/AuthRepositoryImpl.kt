package com.sopt.now.compose.data.repositoryimpl

import com.sopt.now.compose.data.datasource.AuthDataSource
import com.sopt.now.compose.data.datasource.PreferenceDataSource
import com.sopt.now.compose.data.dto.request.RequestLoginDto
import com.sopt.now.compose.data.dto.request.RequestSignUpDto
import com.sopt.now.compose.domain.entity.request.LoginRequestEntity
import com.sopt.now.compose.domain.entity.request.SignupRequestEntity
import com.sopt.now.compose.domain.entity.response.LoginResponseEntity
import com.sopt.now.compose.domain.entity.response.SignupResponseEntity
import com.sopt.now.compose.domain.entity.response.UserResponseEntity
import com.sopt.now.compose.domain.repository.AuthRepository
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : AuthRepository {

    override suspend fun postLogin(request: LoginRequestEntity): Result<LoginResponseEntity> =
        runCatching {
            val response = authDataSource.postLogin(
                request = RequestLoginDto(
                    authenticationId = request.authenticationId,
                    password = request.password
                )
            )
            if (response.isSuccessful) {
                LoginResponseEntity(
                    code = response.code(),
                    memberId = response.headers()[LOCATION] ?: throw Exception(response.message())
                )
            } else {
                throw Exception(
                    JSONObject(
                        response.errorBody()?.string().orEmpty()
                    ).getString(MESSAGE)
                )
            }
        }

    override suspend fun postSignUp(request: SignupRequestEntity): Result<SignupResponseEntity> =
        runCatching {
            val response = authDataSource.postSignUp(
                request = RequestSignUpDto(
                    authenticationId = request.authenticationId,
                    password = request.password,
                    nickname = request.nickname,
                    phone = request.phone
                )
            )
            if (response.isSuccessful) {
                SignupResponseEntity(
                    code = response.code(),
                    memberId = response.headers()[LOCATION] ?: throw Exception(response.message())
                )
            } else {
                throw Exception(
                    JSONObject(
                        response.errorBody()?.string().orEmpty()
                    ).getString(MESSAGE)
                )
            }
        }

    override fun putUserIdInPreference(userId: String) = preferenceDataSource.setUserId(userId)


    companion object {
        private const val MESSAGE = "message"
        private const val LOCATION = "location"
    }
}