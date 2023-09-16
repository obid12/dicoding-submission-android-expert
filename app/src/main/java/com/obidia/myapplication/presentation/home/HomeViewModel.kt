package com.obidia.myapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.domain.usecase.UseCase
import com.obidia.core.utils.Resource
import com.obidia.core.utils.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
  private val _dataUserSearch = MutableStateFlow<Resource<UserSearchEntity?>>(Success(null))

  val dataUserSearch get() = _dataUserSearch

  fun getAllUserSearch(userName: String = "John") {
    viewModelScope.launch {
      useCase.searchUser(userName)
        .catch {
          _dataUserSearch.value = Resource.Error(it)
        }.collect {
          _dataUserSearch.value = it
        }

    }
  }
}