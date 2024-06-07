package com.sopt.now.container

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import com.sopt.now.network.ApiFactory
import com.sopt.now.network.AuthService
import com.sopt.now.container.repository.AuthRepository
import com.sopt.now.container.repository.AuthRepositoryImpl
import com.sopt.now.login.LoginActivity
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class SoptAppContainer(
    val context: Context
): AppContainer {

    override val authRepository: AuthRepository by lazy {
        val authService = create<AuthService>(retrofitBeforeLogin)
        AuthRepositoryImpl(authService)
    }

    override val authAfterLoinRepostory: AuthRepository by lazy {
        val authService = create<AuthService>(retrofitAfterLogin)
        AuthRepositoryImpl(authService)
    }

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

    val retrofitAfterLogin: Retrofit by lazy {
         Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(createHttpClient().build())
            .build()
    }

    private fun createHttpClient(): OkHttpClient.Builder {
        val sharedPreferences = context.getSharedPreferences(LoginActivity.PREFERENCE_KEY, Context.MODE_PRIVATE)
        val memberId = sharedPreferences.getString(LoginActivity.MEMBER_ID_KEY, "")?.ifEmpty { "" }!!

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    MEMBER_ID_HEADER,    // Header 이름
                    memberId
                ).build()
                chain.proceed(request)
            })
        }

        return okHttpClient
    }


    private inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create(T::class.java)

    companion object {
        const val BASE_URL = BuildConfig.AUTH_BASE_URL
        const val MEMBER_ID_HEADER = "memberId"
    }
}