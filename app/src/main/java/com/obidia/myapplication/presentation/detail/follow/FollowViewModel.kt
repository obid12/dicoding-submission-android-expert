package com.obidia.myapplication.presentation.detail.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.usecase.UseCase
import com.obidia.core.utils.Resource
import com.obidia.core.utils.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
  private val useCase: UseCase
) : ViewModel() {

  private val _dataFollower =
    MutableStateFlow<Resource<ArrayList<ItemUserSearchEntity>?>>(Success(null))
  val dataFollower get() = _dataFollower

  private val _dataFollowing =
    MutableStateFlow<Resource<ArrayList<ItemUserSearchEntity>?>>(Success(null))
  val dataFollowing get() = _dataFollowing

  fun getListFollower(userName: String) {
    viewModelScope.launch {
      useCase.getListFollowers(userName).catch {
        _dataFollower.value = Resource.Error(it)
      }.collect {
        _dataFollower.value = it
      }
    }
  }

  fun getListFollowing(userName: String) {
    viewModelScope.launch {
      useCase.getListFollowing(userName).catch {
        _dataFollowing.value = Resource.Error(it)
      }.collect {
        _dataFollowing.value = it
      }
    }
  }
}