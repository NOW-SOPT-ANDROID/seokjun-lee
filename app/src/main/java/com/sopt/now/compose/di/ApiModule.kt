package com.sopt.now.compose.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import com.sopt.now.compose.di.qualifier.AUTH
import com.sopt.now.compose.di.qualifier.HEADER
import com.sopt.now.compose.di.qualifier.REQRES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val CONTENT_TYPE = "application/json"

    @Singleton
    @Provides
    fun provideJsonConverter(): Converter.Factory {
        return Json.asConverterFactory(CONTENT_TYPE.toMediaType())
    }

    @Singleton
    @Provides
    @HEADER
    fun provideHeaderOkHttpClient(
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()

    @Singleton
    @Provides
    @AUTH
    fun provideAuthRetrofit(
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AUTH_BASE_URL)
            .addConverterFactory(converter)
            .build()
    }

    @Singleton
    @Provides
    @HEADER
    fun provideHeaderRetrofit(
        client: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTH_BASE_URL)
        .addConverterFactory(converter)
        .client(client)
        .build()

    @Singleton
    @Provides
    @REQRES
    fun provideReqresRetrofit(
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AUTH_BASE_URL)
            .addConverterFactory(converter)
            .build()
    }

}