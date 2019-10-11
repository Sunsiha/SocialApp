package com.moonlyte.socialapp.features.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonlyte.socialapp.features.post.model.Posts
import com.moonlyte.socialapp.features.post.model.PostsModel
import com.moonlyte.socialapp.features.post.repository.PostRepository
import com.moonlyte.socialapp.persistence.DataSource

class PostsViewModel : ViewModel() {

    private val postsModel = PostsModel()
    private val postRepository = PostRepository()

    val postLiveData = MutableLiveData<List<Posts>>()

    fun loadPosts(userId: Int,dataSource: DataSource) {
        this.postRepository.getPosts(userId,dataSource, {
            if (it.isSuccessful) {
                if (!it.body().isNullOrEmpty()) {
                    // notify user list through data binding
                    this.postsModel.postsList = it.body()!!

                    // notify UI through live data
                    this.postLiveData.postValue(it.body()!!)
                } else {
                    // Show Empty View.
                }
            } else {
                // Show error.
                Log.e("PostsViewModel", "Post service response unsuccessful.")
            }
        }, { list, t ->
            if (!list.isNullOrEmpty()) {
                // notify user list through data binding
                this.postsModel.postsList = list

                // notify UI through live data
                this.postLiveData.postValue(list)
            } else {
                // Show error.
                Log.e("PostsViewModel", "loadPosts: ${t.message}")
            }
        })
    }

    fun getPostsModel(): PostsModel {
        return this.postsModel
    }
}