package com.obidia.favorite

import androidx.lifecycle.ViewModel
import com.obidia.core.domain.model.UserModel
import com.obidia.core.domain.usecase.UseCase
import com.obidia.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(
  private val useCase: UseCase
) : ViewModel() {

  fun getFavoriteUser(): Flow<Resource<ArrayList<UserModel>>> {
    return useCase.getAllUser()
  }
}