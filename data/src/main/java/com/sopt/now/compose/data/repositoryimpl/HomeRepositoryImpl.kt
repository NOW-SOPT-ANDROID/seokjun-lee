package com.sopt.now.compose.data.repositoryimpl

import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.domain.entity.response.UserResponseEntity
import com.sopt.now.compose.domain.repository.HomeRepository
import org.json.JSONObject
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {

    override suspend fun getUser(): Result<UserResponseEntity> =
        runCatching {
            val response = homeDataSource.getUserInfo()
            if (response.isSuccessful) {
                with(response.body()!!.data) {
                    UserResponseEntity(
                        authenticationId = authenticationId,
                        nickname = nickname,
                        phone = phone
                    )
                }
            } else {
                throw Exception(
                    JSONObject(
                        response.errorBody()?.string().orEmpty()
                    ).getString(MESSAGE)
                )
            }
        }

    companion object {
        private const val MESSAGE = "message"
    }
}