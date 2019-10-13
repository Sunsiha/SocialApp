package com.moonlyte.socialapp.features.account

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moonlyte.myjavalibrary.ToDosActivity
import com.moonlyte.myjavalibrary.UsersActivity
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.databinding.ActivityUsersBinding
import com.moonlyte.socialapp.features.account.adapter.UsersAdapter
import com.moonlyte.socialapp.features.account.adapter.UsersAdapter.Companion.POST
import com.moonlyte.socialapp.features.account.adapter.UsersAdapter.Companion.TODO
import com.moonlyte.socialapp.features.post.PostActivity
import com.moonlyte.socialapp.persistence.DataSource

class UsersActivity : AppCompatActivity() {

    private val usersViewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this).get(UsersViewModel::class.java)
    }

    private lateinit var activityUsersBinding: ActivityUsersBinding

    private lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUsersBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        activityUsersBinding.viewModel = this.usersViewModel
        activityUsersBinding.handler = this

        val usersAdapter = UsersAdapter { user, section ->
            when(section) {
                POST -> this.startActivity(Intent(this, PostActivity::class.java).apply {
                    putExtra("userId", user.id)
                })
                TODO -> this.startActivity(Intent(this, ToDosActivity::class.java).apply {
                    putExtra("userId", user.id)
                })
                else -> {}
            }
        }

        activityUsersBinding.adapter = usersAdapter
        this.dataSource = DataSource.getInstance(this)

        this.usersViewModel.loadUsers(dataSource)

        this.usersViewModel.userLiveData.observe(this, Observer {
            val count: Int = dataSource.getAppDatabase().userDao().getCount()
            Log.d("UserActivity", "User Data count: $count")
        })
    }

    fun onRedirectAction() {
        val i = Intent(this, UsersActivity::class.java)
        startActivity(i)
    }

    fun openApp(context: Context, packageName: String): Boolean {
        val manager = context.getPackageManager()
        try {
            val i = manager.getLaunchIntentForPackage(packageName)
                ?: return false
            //throw new ActivityNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(i)
            return true
        } catch (e: ActivityNotFoundException) {
            return false
        }
    }

}

