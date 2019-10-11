package com.moonlyte.socialapp.features.post.repository

import android.util.Log
import com.moonlyte.socialapp.features.post.model.Posts
import com.moonlyte.socialapp.network.ApiClient
import com.moonlyte.socialapp.persistence.DataSource
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRepository {

    private var api: PostsAPI = ApiClient.getApiClient().create(PostsAPI::class.java)

    fun getPosts(
        userId:Int,
        dataSource: DataSource,
        success: ((Response<List<Posts>>) -> Unit)?,
        failure: ((List<Posts>, t: Throwable) -> Unit)? = null
    ) {
        doAsync {
            api.getUserPosts(userId).enqueue(object : Callback<List<Posts>> {
                override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                    val postList = dataSource.getAppDatabase().postDao().getPostsUserId(userId)
                    Log.d("PostsRepository", "Offline posts: ${postList.isNullOrEmpty()} ")
                    failure?.invoke(postList, t)
                }

                override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>?) {
                    response?.let {
                        it.body()?.let {list ->
                            dataSource.getAppDatabase().postDao().insertAllPost(list)
                        }

                        success?.invoke(it)
                    }
                }
            })
        }
    }
}