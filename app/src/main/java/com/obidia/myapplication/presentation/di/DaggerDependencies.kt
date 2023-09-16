package com.obidia.myapplication.presentation.di

import com.obidia.core.domain.usecase.UseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerDependencies{
  fun useCase(): UseCase
}