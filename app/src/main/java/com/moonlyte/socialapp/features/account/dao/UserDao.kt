package com.moonlyte.socialapp.features.account.dao

import androidx.room.*
import com.moonlyte.socialapp.features.account.model.Users

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): List<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: Users)

    @Insert
    fun insertAll(vararg users: Users)

    @Query("SELECT COUNT(*) FROM user")
    fun getCount(): Int

    @Delete
    fun delete(users: Users)

    @Update
    fun update(users: Users)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<Users>)

}