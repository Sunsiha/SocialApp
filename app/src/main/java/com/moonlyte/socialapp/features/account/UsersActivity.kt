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
import com.moonlyte.myjavalibrary.UsersActivity
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.features.account.adapter.UsersAdapter
import com.moonlyte.socialapp.databinding.ActivityUsersBinding
import com.moonlyte.socialapp.persistence.DataSource
import com.moonlyte.socialapp.features.post.PostActivity

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

        val usersAdapter = UsersAdapter { user ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("UserId", user.id)
            this.startActivity(intent)
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

