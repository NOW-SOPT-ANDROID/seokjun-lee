package com.sopt.now.compose.di

import com.sopt.now.compose.data.datasource.AuthDataSource
import com.sopt.now.compose.data.datasource.FollowerDataSource
import com.sopt.now.compose.data.datasource.HomeDataSource
import com.sopt.now.compose.data.datasource.PreferenceDataSource
import com.sopt.now.compose.data.datasourceimpl.AuthDataSourceImpl
import com.sopt.now.compose.data.datasourceimpl.FollowerDataSourceImpl
import com.sopt.now.compose.data.datasourceimpl.HomeDataSourceImpl
import com.sopt.now.compose.data.datasourceimpl.PreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun provideFollowerDataSource(followerDataSourceImpl: FollowerDataSourceImpl): FollowerDataSource

    @Binds
    @Singleton
    abstract fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource

    @Binds
    @Singleton
    abstract fun providePreferenceDataSource(preferenceDataSourceImpl: PreferenceDataSourceImpl): PreferenceDataSource
}