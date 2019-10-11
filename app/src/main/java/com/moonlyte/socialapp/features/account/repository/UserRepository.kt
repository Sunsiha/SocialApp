package com.moonlyte.socialapp.features.account.repository

import android.util.Log
import com.moonlyte.socialapp.features.account.model.Users
import com.moonlyte.socialapp.network.ApiClient
import com.moonlyte.socialapp.persistence.DataSource
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private var api: UsersAPI = ApiClient.getApiClient().create(UsersAPI::class.java)

    fun getUsers(
        dataSource: DataSource,
        success: ((Response<List<Users>>) -> Unit)?,
        failure: ((List<Users>, t: Throwable) -> Unit)? = null
    ) {
        doAsync {
            api.getUsers().enqueue(object : Callback<List<Users>> {
                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    val userList = dataSource.getAppDatabase().userDao().getUsers()
                    Log.d("UserRepository", "Offline users: ${userList.isNullOrEmpty()} ")
                    failure?.invoke(userList, t)
                }

                override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>?) {
                    response?.let {
                        it.body()?.let {list ->
                            dataSource.getAppDatabase().userDao().insertAllUsers(list)
                        }

                        success?.invoke(it)
                    }
                }
            })
        }
    }
}