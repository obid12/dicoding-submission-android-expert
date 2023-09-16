package com.obidia.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obidia.core.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

  abstract fun userDao(): UserDao

}