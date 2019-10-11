package com.moonlyte.socialapp.features.post.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moonlyte.socialapp.features.post.model.Posts

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getPosts(): List<Posts>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPost(posts: List<Posts>)

    @Query("SELECT COUNT(*) FROM posts")
    fun getCount(): Int

    @Query("SELECT * FROM posts WHERE userId = :userId")
    fun getPostsUserId(userId:Int): List<Posts>

}