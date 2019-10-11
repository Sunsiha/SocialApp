package com.moonlyte.socialapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moonlyte.socialapp.features.account.dao.UserDao
import com.moonlyte.socialapp.features.account.model.Users
import com.moonlyte.socialapp.features.post.dao.PostDao
import com.moonlyte.socialapp.features.post.model.Posts

@Database(entities = [Users::class, Posts::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

}