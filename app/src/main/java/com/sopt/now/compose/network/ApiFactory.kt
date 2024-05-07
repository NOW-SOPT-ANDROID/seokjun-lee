package com.sopt.now.compose.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL: String = BuildConfig.AUTH_BASE_URL

    lateinit var retrofitAfterLogin: TemporaryAuthService
    val retrofitBeforeLogin: Retrofit by lazy {
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
            .create(TemporaryAuthService::class.java)
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

    inline fun <reified T> create(): T = retrofitBeforeLogin.create(T::class.java)
    inline fun <reified T> createFollow(): T = retrofitFollow.create(T::class.java)

}

object ServicePool {
    val temporaryAuthService = ApiFactory.create<TemporaryAuthService>()
    val followService = ApiFactory.createFollow<FollowService>()

    lateinit var mainService: TemporaryAuthService
    fun initMainService(memberId: String) {
        ApiFactory.createRetrofitWithMemberId(memberId)
        mainService = ApiFactory.retrofitAfterLogin
    }
}