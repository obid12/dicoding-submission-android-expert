package com.obidia.favorite.di

import android.content.Context
import com.obidia.favorite.FavoriteFragment
import com.obidia.myapplication.presentation.di.DaggerDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
  dependencies = [DaggerDependencies::class],
)
interface FavoriteComponent {
  fun inject(favoriteFragment: FavoriteFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun appDependencies(daggerDependencies: DaggerDependencies): Builder
    fun build(): FavoriteComponent
  }
}