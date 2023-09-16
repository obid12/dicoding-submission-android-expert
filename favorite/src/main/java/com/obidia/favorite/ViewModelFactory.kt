package com.obidia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obidia.core.domain.usecase.UseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCase: UseCase):ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
      return FavoriteViewModel(useCase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}