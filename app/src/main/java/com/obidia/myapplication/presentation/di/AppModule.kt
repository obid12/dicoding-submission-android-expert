package com.obidia.myapplication.presentation.di

import com.obidia.core.domain.usecase.UseCase
import com.obidia.core.domain.usecase.UseCaseImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
  @Binds
  @Singleton
  abstract fun provideUseCase(useCaseImplementation: UseCaseImplementation): UseCase
}