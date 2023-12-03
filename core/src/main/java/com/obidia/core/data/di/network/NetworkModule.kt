package com.obidia.core.data.di.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.obidia.core.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  fun provideRetrofit(okHttp: OkHttpClient, @ApplicationContext context: Context): Retrofit {
    return Retrofit.Builder().apply {
      addConverterFactory(GsonConverterFactory.create())
      client(
        okHttp.newBuilder().addInterceptor(
          ChuckerInterceptor.Builder(context)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
        ).build()
      )
      baseUrl(BuildConfig.API_BASE_URL)
    }.build()
  }

  @Provides
  fun provideOkHttp(requestInterceptor: RequestInterceptor): OkHttpClient =
    OkHttpClient.Builder().apply {
      connectTimeout(60, TimeUnit.SECONDS)
      writeTimeout(60, TimeUnit.SECONDS)
      readTimeout(60, TimeUnit.SECONDS)
      addInterceptor(requestInterceptor)
    }.build()

  @Provides
  fun provideRequestInterceptor(): RequestInterceptor =
    RequestInterceptor()
}