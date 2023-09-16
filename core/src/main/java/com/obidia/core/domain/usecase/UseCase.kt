package com.obidia.core.domain.usecase

import com.obidia.core.domain.model.ItemUserSearchEntity
import com.obidia.core.domain.model.UserDetailEntity
import com.obidia.core.domain.model.UserModel
import com.obidia.core.domain.model.UserSearchEntity
import com.obidia.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase {

  fun cekSameDataUser(username: String): Flow<ArrayList<UserModel>>

  fun deleteUser(data: String)

  fun addUser(user: UserModel)

  fun getAllUser(): Flow<Resource<ArrayList<UserModel>>>

  suspend fun getDetailUser(name: String): Flow<Resource<UserDetailEntity>>

  fun searchUser(searchQuery: String?): Flow<Resource<UserSearchEntity>>

  fun getListFollowers(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>>

  fun getListFollowing(username: String): Flow<Resource<ArrayList<ItemUserSearchEntity>>>
}