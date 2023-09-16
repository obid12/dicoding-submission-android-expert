package com.obidia.core.domain.usecase

import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserModel
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.domain.repo.Repository
import com.obidia.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCaseImplementation @Inject constructor(
  private val repository: Repository
) : UseCase {

  override fun cekSameDataUser(username: String): Flow<ArrayList<UserModel>> {
    return repository.cekSameDataUser(username)
  }

  override fun deleteUser(data: String) {
    repository.deleteUser(data)
  }

  override fun addUser(user: UserModel) {
    repository.addUser(user)
  }

  override fun getAllUser(): Flow<Resource<ArrayList<UserModel>>> {
    return repository.getAllUser()
  }

  override suspend fun getDetailUser(name: String): Flow<Resource<UserDetailEntity>> {
    return repository.getDetailUser(name)
  }

  override fun searchUser(searchQuery: String?): Flow<Resource<UserSearchEntity>> {
    return repository.searchUser(searchQuery)
  }

  override fun getListFollowers(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return repository.getListFollowers(username)
  }

  override fun getListFollowing(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>> {
    return repository.getListFollowing(username)
  }

}