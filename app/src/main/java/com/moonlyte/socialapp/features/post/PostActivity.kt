package com.moonlyte.socialapp.features.post

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.databinding.ActivityPostingBinding
import com.moonlyte.socialapp.persistence.DataSource
import com.moonlyte.socialapp.features.post.adapter.PostAdapter

class PostActivity : AppCompatActivity() {

    private val postsViewModel: PostsViewModel by lazy {
        ViewModelProviders.of(this).get(PostsViewModel::class.java)
    }

    private lateinit var dataSource: DataSource

    private lateinit var activityPostBinding: ActivityPostingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_posting)

        activityPostBinding.postViewModels = this.postsViewModel

        val postsAdapter = PostAdapter()
        activityPostBinding.postsAdapter = postsAdapter

        val userId = intent.getIntExtra("userId", 0)

        this.dataSource = DataSource.getInstance(this)

        this.postsViewModel.loadPosts(userId,dataSource)

        this.postsViewModel.postLiveData.observe(this, Observer {

            val count: Int = dataSource.getAppDatabase().postDao().getCount()
            Log.d("PostsActivity", "Post Data count: $count")
        })
    }
}
