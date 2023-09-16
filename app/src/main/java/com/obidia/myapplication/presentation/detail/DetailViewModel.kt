package com.obidia.myapplication.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserModel
import com.obidia.core.domain.usecase.UseCase
import com.obidia.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: UseCase) :
  ViewModel() {

  private val _dataDetailUser =
    MutableStateFlow<Resource<UserDetailEntity?>>(Resource.Success(null))
  val dataDetailUser get() = _dataDetailUser

  private val _cekData: MutableStateFlow<Boolean?> = MutableStateFlow(null)
  val cekData get() = _cekData.asStateFlow()

  fun getDetailUser(name: String) {
    viewModelScope.launch {
      useCase.getDetailUser(name).catch {
        _dataDetailUser.value = Resource.Error(it)
      }.collect {
        _dataDetailUser.value = it
      }
    }
  }

  fun cekSameData(userName: String) {
    viewModelScope.launch(Dispatchers.IO) {
      useCase.cekSameDataUser(userName).catch {}.collect {
        Log.d("Kesini data", it.toString())
        _cekData.value = it.isNotEmpty()
        Log.d("Kesini boolean viewmodel", _cekData.value.toString())
      }
    }
  }

  fun insertDataUser(user: UserModel) {
    viewModelScope.launch(Dispatchers.IO) {
      useCase.addUser(user)
    }
  }

  fun deleteUser(user: String) {
    viewModelScope.launch(Dispatchers.IO) {
      useCase.deleteUser(user)
    }
  }

}