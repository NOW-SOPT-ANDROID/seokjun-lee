package com.sopt.now.compose.container

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import com.sopt.now.compose.models.Follow
import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.FollowService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer{
    val userRepository: PreferenceUserRepository
    val followRepository: FollowRepository
}

class SoptAppContainer(context: Context): AppContainer {
    lateinit var retrofitAfterLogin: AuthService

    val retrofitLogin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    val retrofitFollow: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    fun createRetrofitWithMemberId(memberId: String) {
        retrofitAfterLogin = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(createHttpClientWithId(memberId).build())
            .build()
            .create(AuthService::class.java)
    }

    private fun createHttpClientWithId(memberId: String): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    "memberId",    // Header 이름
                    memberId
                ).build()
                chain.proceed(request)
            })
        }

    override val userRepository: PreferenceUserRepository by lazy {
        PreferenceUserRepository(context.getSharedPreferences("SOPT", Context.MODE_PRIVATE))
    }

    override val followRepository: FollowRepository by lazy {
        FollowRepository(followService = retrofitFollow.create(FollowService::class.java))
    }

    companion object{
        const val BASE_URL = BuildConfig.AUTH_BASE_URL
    }
}