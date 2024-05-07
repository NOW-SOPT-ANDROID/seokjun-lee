package com.sopt.now.compose.container

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import com.sopt.now.compose.container.PreferenceUserRepository.Companion.USER_ID_KEY
import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.FollowService
import com.sopt.now.compose.network.TempAuthService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create


interface AppContainer{
    val userRepository: PreferenceUserRepository
    val followRepository: NetworkFollowerRepository
    val authRepository: NetworkAuthRepository
}

class SoptAppContainer(context: Context): AppContainer {

    override val userRepository: PreferenceUserRepository by lazy {
        PreferenceUserRepository(context.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE))
    }

    override val followRepository: NetworkFollowerRepository by lazy {
        NetworkFollowerRepository(followService = retrofitFollower.create(FollowService::class.java))
    }

    override val authRepository: NetworkAuthRepository by lazy {
        NetworkAuthRepository(authService = retrofitUser.create(TempAuthService::class.java))
    }

    val retrofitLogin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    private val retrofitFollower: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val retrofitUser: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClientWithInterceptor.build())
            .build()
    }

    private val okHttpClientWithInterceptor: OkHttpClient.Builder by lazy {
        val memberId = context
            .getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE)
            .getString(USER_ID_KEY, "")
            .orEmpty()

        OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    name = "memberId",    // Header 이름
                    value = memberId
                ).build()
                chain.proceed(request)
            })
        }
    }

    companion object{
        const val BASE_URL = BuildConfig.AUTH_BASE_URL
        const val PREFERENCE_ID = "SOPT"

        private const val TAG = "SoptAppContainer"
    }
}