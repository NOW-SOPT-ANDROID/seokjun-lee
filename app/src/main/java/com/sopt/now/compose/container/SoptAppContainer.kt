package com.sopt.now.compose.container

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import com.sopt.now.compose.container.impl.AuthRepositoryImpl
import com.sopt.now.compose.container.impl.FollowerRepositoryImpl
import com.sopt.now.compose.container.impl.MemberRepositoryImpl
import com.sopt.now.compose.container.impl.UserRepositoryImpl
import com.sopt.now.compose.network.AuthService
import com.sopt.now.compose.network.FollowService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


class SoptAppContainer(context: Context): AppContainer {

    override val userRepository: UserRepositoryImpl by lazy {
        UserRepositoryImpl(context.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE))
    }

    override val followRepository: FollowerRepositoryImpl by lazy {
        FollowerRepositoryImpl(followService = retrofitFollower.create(FollowService::class.java))
    }

    override val memberRepository: MemberRepositoryImpl by lazy {
        MemberRepositoryImpl(authService = retrofitUser.create(AuthService::class.java))
    }

    override val authRepository: AuthRepositoryImpl by lazy {
        AuthRepositoryImpl(authService =  retrofitLogin.create(AuthService::class.java))
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
        val memberId = userRepository.getUserId()

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
    }
}