package com.sopt.now.compose.data.repositoryimpl

import com.sopt.now.compose.data.datasource.FollowerDataSource
import com.sopt.now.compose.data.dto.request.RequestFollowerDto
import com.sopt.now.compose.domain.entity.request.FollowerRequestEntity
import com.sopt.now.compose.domain.entity.response.FollowerResponseEntity
import com.sopt.now.compose.domain.repository.FollowerRepository
import org.json.JSONObject
import javax.inject.Inject

class FollowerRepositoryImpl @Inject constructor(
    private val followerDataSource: FollowerDataSource
) : FollowerRepository {

    override suspend fun getFollowers(request: FollowerRequestEntity): Result<List<FollowerResponseEntity>> =
        runCatching {
            val dto = RequestFollowerDto(page = request.page)
            val response = followerDataSource.getFollowerList(dto)
            if (response.isSuccessful) {
                with(response.body()?.data!!) {
                    map {
                        FollowerResponseEntity(
                            id = it.id,
                            email = it.email,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            avatar = it.avatar
                        )
                    }
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