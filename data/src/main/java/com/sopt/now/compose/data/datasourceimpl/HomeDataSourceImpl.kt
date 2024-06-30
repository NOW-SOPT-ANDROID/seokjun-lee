package com.sopt.now.compose.data.datasourceimpl

import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.data.dto.response.ResponseUserDto
import com.sopt.now.compose.data.service.HomeService
import retrofit2.Response
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeService: HomeService
): HomeDataSource{
    override suspend fun getUserInfo():Response<ResponseUserDto> =
        homeService.getMemberInfo()
}