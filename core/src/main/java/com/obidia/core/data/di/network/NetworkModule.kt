package com.obidia.core.data.di.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.obidia.core.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  fun provideRetrofit(okHttp: OkHttpClient, @ApplicationContext context: Context): Retrofit {
    val hostname = "api.github.com"
    val certificatePinner = CertificatePinner.Builder()
      .add(hostname, "sha256/jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
      .add(hostname, "sha256/Jg78dOE+fydIGk19swWwiypUSR6HWZybfnJG/8G7pyM=")
      .build()
    return Retrofit.Builder().apply {
      addConverterFactory(GsonConverterFactory.create())
      client(
        okHttp.newBuilder().addInterceptor(
          ChuckerInterceptor.Builder(context)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
        ).certificatePinner(certificatePinner).build()
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