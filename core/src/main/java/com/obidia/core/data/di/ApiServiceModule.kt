package com.obidia.core.data.di

import com.obidia.core.data.di.network.NetworkModule
import com.obidia.core.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

  @Provides
  fun provideApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }
}