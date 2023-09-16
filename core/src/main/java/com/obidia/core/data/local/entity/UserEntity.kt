package com.obidia.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obidia.core.domain.model.UserModel

@Entity(tableName = "user")
data class UserEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "userId")
  var userId: Int,
  @ColumnInfo(name = "avatarUrl")
  val avatarUrl: String,
  @ColumnInfo(name = "login")
  val login: String
) {
  companion object {
    fun transform(response: List<UserEntity>): ArrayList<UserModel> {
      return ArrayList(
        response.map {
          UserModel(
            it.userId,
            it.avatarUrl,
            it.login
          )
        }
      )
    }

    fun transform(model: UserModel): UserEntity {
      return UserEntity(
        model.userId,
        model.avatarUrl,
        model.login
      )
    }

    fun transform(model: UserEntity): UserModel = UserModel(
      model.userId,
      model.avatarUrl,
      model.login
    )
  }
}