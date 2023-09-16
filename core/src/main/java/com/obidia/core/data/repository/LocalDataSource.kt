package com.obidia.core.data.repository

import com.obidia.core.data.local.entity.UserEntity
import com.obidia.core.data.local.room.UserDao
import com.obidia.core.domain.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
  private val dao: UserDao
) {
  fun cekSameDataUser(username: String): Flow<ArrayList<UserModel>> {
    return dao.checkSameDataUser(username).map {
      UserEntity.transform(it)
    }.flowOn(Dispatchers.IO)
  }


  fun deleteUser(login:String) {
    dao.deleteUser(login)
  }

  fun addUser(user: UserModel) {
    dao.addUser(UserEntity.transform(user))
  }

  fun getAllUser(): Flow<ArrayList<UserModel>> {
    return dao.getAllUser().map {
      UserEntity.transform(it)
    }.flowOn(Dispatchers.IO)
  }
}