package com.obidia.core.data.repository

import com.obidia.core.data.remote.api.ApiService
import com.obidia.core.data.remote.response.ItemUserSearchResponse
import com.obidia.core.data.remote.response.UserDetailResponse
import com.obidia.core.data.remote.response.UserSearchResponse
import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.utils.Resource
import com.obidia.core.utils.Resource.Loading
import com.obidia.core.utils.Resource.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
  private val apiService: ApiService
) {

  fun getDetailUser(name: String): Flow<Resource<UserDetailEntity>> {
    return flow {
      emit(Loading)
      try {
        val response = apiService.getDetailUser(name)
        response.body()?.let {
          val data = UserDetailResponse.transform(it)
          emit(Success(data))
        } ?: kotlin.run {
          throw HttpException(response)
        }
      } catch (e: Throwable) {
        emit(Resource.Error(e))
      }
    }
  }

  fun searchUser(searchQuery: String?): Flow<Resource<UserSearchEntity>> {
    return flow {
      emit(Loading)
      delay(1000)
      try {
        val response = apiService.searchUser(searchQuery)
        response.body()?.let {
          val data = UserSearchResponse.transform(it)
          emit(Success(data))
        } ?: kotlin.run {
          throw HttpException(response)
        }
      } catch (e: Throwable) {
        emit(Resource.Error(e))
      }
    }
  }

  fun getListFollowers(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return flow {
      emit(Loading)
      try {
        val response = apiService.getListFollowers(username)
        response.body()?.let {
          val data = ItemUserSearchResponse.transform(it)
          emit(Success(data))
        } ?: kotlin.run {
          throw HttpException(response)
        }
      } catch (e: Throwable) {
        emit(Resource.Error(e))
      }
    }
  }

  fun getListFollowing(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return flow {
      emit(Loading)
      try {
        val response = apiService.getListFollowing(username)
        response.body()?.let {
          val data = ItemUserSearchResponse.transform(it)
          emit(Success(data))
        } ?: kotlin.run {
          throw HttpException(response)
        }
      } catch (e: Throwable) {
        emit(Resource.Error(e))
      }
    }
  }

}