package com.sopt.now.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.create

object ApiFactory {
    private const val BASE_URL: String = BuildConfig.AUTH_BASE_URL

    lateinit var retrofitAfterLogin: AuthService

    val retrofitBeforeLogin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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

    inline fun <reified T> create(): T = retrofitBeforeLogin.create(T::class.java)

}

object ServicePool {
    val authService = ApiFactory.create<AuthService>()
    lateinit var mainService: AuthService

    fun initMainService(memberId: String) {
        ApiFactory.createRetrofitWithMemberId(memberId)
        mainService = ApiFactory.retrofitAfterLogin
    }
}