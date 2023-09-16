package com.obidia.core.data.repository

import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserModel
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.domain.repo.Repository
import com.obidia.core.utils.Resource
import com.obidia.core.utils.Resource.Loading
import com.obidia.core.utils.Resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImplementation @Inject constructor(
  private val remoteDataSource: RemoteDataSource,
  private val localDataSource: LocalDataSource
) : Repository {

  override fun cekSameDataUser(username: String): Flow<ArrayList<UserModel>> {
    return localDataSource.cekSameDataUser(username)
  }

  override fun deleteUser(data: String) {
    localDataSource.deleteUser(data)
  }

  override fun addUser(user: UserModel) {
    localDataSource.addUser(user)
  }

  override fun getAllUser(): Flow<Resource<ArrayList<UserModel>>> {
    return flow {
      val data = localDataSource.getAllUser()
      data.onStart { emit(Loading) }.catch {
        emit(Resource.Error(it))
      }.collect {
        emit(Success(it))
      }
    }
  }

  override fun getDetailUser(name: String): Flow<Resource<UserDetailEntity>> {
    return remoteDataSource.getDetailUser(name)
  }

  override fun searchUser(searchQuery: String?): Flow<Resource<UserSearchEntity>> {
    return remoteDataSource.searchUser(searchQuery)
  }

  override fun getListFollowers(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return remoteDataSource.getListFollowers(username)
  }

  override fun getListFollowing(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return remoteDataSource.getListFollowing(username)
  }

}