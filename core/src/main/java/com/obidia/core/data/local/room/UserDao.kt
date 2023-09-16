package com.obidia.core.data.local.room

import androidx.room.*
import com.obidia.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

  @Query("SELECT * FROM user")
  fun getAllUser(): Flow<List<UserEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addUser(user: UserEntity)

  @Query("DELETE FROM user WHERE login = :login")
  fun deleteUser(login: String)

  @Query("SELECT * FROM user WHERE login = :login")
  fun checkSameDataUser(login: String): Flow<List<UserEntity>>

}
