package com.obidia.core.data.di

import android.content.Context
import androidx.room.Room
import com.obidia.core.data.local.room.UserDao
import com.obidia.core.data.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("obidia".toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(
      context,
      UserDatabase::class.java, "User.db"
    ).fallbackToDestructiveMigration()
      .openHelperFactory(factory)
      .build()
  }

  @Singleton
  @Provides
  fun provideTourismDao(database: UserDatabase): UserDao = database.userDao()
}