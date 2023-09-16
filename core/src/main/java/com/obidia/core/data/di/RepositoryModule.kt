package com.obidia.core.data.di

import com.obidia.core.data.repository.RepositoryImplementation
import com.obidia.core.domain.repo.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiServiceModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  abstract fun provideRepository(
    repositoryImplementation: RepositoryImplementation
  ): Repository
}